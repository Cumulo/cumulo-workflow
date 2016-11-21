
(ns workflow-server.updater.state (:require [workflow-server.schema :as schema]))

(defn disconnect [db op-data state-id op-id op-time]
  (update db :states (fn [state] (dissoc state state-id))))

(defn remove-notification [db op-data state-id op-id op-time]
  (update-in
   db
   [:states state-id :notifications]
   (fn [notifications] (subvec notifications 0 op-data))))

(defn connect [db op-data state-id op-id op-time]
  (assoc-in db [:states state-id] (merge schema/state {:id state-id})))
