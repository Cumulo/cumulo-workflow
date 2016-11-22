
(ns workflow-server.updater.user (:require [workflow-server.util :refer [find-first]]))

(defn log-out [db op-data state-id op-id op-time]
  (assoc-in db [:states state-id :user-id] nil))

(defn sign-up [db op-data state-id op-id op-time]
  (let [[username password] op-data
        maybe-user (find-first (fn [user] (= username (:name user))) (vals (:users db)))]
    (if (some? maybe-user)
      (update-in
       db
       [:states state-id :notifications]
       (fn [notifications]
         (conj
          notifications
          {:id op-id, :kind :attentive, :text (str "Name is token: " username)})))
      (-> db
          (assoc-in [:states state-id :user-id] op-id)
          (assoc-in
           [:users op-id]
           {:password password, :name username, :nickname username, :id op-id, :avatar nil})))))

(defn log-in [db op-data state-id op-id op-time]
  (let [[username password] op-data
        maybe-user (find-first
                    (fn [user] (and (= username (:name user))))
                    (vals (:users db)))]
    (update-in
     db
     [:states state-id]
     (fn [state]
       (if (some? maybe-user)
         (if (= password (:password maybe-user))
           (assoc state :user-id (:id maybe-user))
           (update
            state
            :notifications
            (fn [notifications]
              (conj
               notifications
               {:id op-id, :kind :attentive, :text (str "Wrong password for " username)}))))
         (update
          state
          :notifications
          (fn [notifications]
            (conj
             notifications
             {:id op-id, :kind :attentive, :text (str "No user named: " username)}))))))))
