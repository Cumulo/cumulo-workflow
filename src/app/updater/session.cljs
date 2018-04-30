
(ns app.updater.session (:require [app.schema :as schema]))

(defn connect [db op-data sid op-id op-time]
  (assoc-in db [:sessions sid] (merge schema/session {:id sid})))

(defn disconnect [db op-data sid op-id op-time]
  (update db :sessions (fn [session] (dissoc session sid))))

(defn remove-notification [db op-data sid op-id op-time]
  (update-in
   db
   [:sessions sid :notifications]
   (fn [notifications] (subvec notifications 0 op-data))))
