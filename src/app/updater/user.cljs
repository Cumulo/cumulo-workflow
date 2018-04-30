
(ns app.updater.user (:require [app.util :refer [find-first]] ["md5" :as md5]))

(defn log-in [db op-data sid op-id op-time]
  (let [[username password] op-data
        maybe-user (find-first
                    (fn [user] (and (= username (:name user))))
                    (vals (:users db)))]
    (update-in
     db
     [:sessions sid]
     (fn [session]
       (if (some? maybe-user)
         (if (= (md5 password) (:password maybe-user))
           (assoc session :user-id (:id maybe-user))
           (update
            session
            :notifications
            (fn [notifications]
              (conj
               notifications
               {:id op-id, :kind :attentive, :text (str "Wrong password for " username)}))))
         (update
          session
          :notifications
          (fn [notifications]
            (conj
             notifications
             {:id op-id, :kind :attentive, :text (str "No user named: " username)}))))))))

(defn log-out [db op-data sid op-id op-time] (assoc-in db [:sessions sid :user-id] nil))

(defn sign-up [db op-data sid op-id op-time]
  (let [[username password] op-data
        maybe-user (find-first (fn [user] (= username (:name user))) (vals (:users db)))]
    (if (some? maybe-user)
      (update-in
       db
       [:sessions sid :notifications]
       (fn [notifications]
         (conj
          notifications
          {:id op-id, :kind :attentive, :text (str "Name is token: " username)})))
      (-> db
          (assoc-in [:sessions sid :user-id] op-id)
          (assoc-in
           [:users op-id]
           {:id op-id,
            :name username,
            :nickname username,
            :password (md5 password),
            :avatar nil})))))
