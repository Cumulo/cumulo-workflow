
(ns tiye-server.updater.state
  (:require [tiye-server.schema :as schema]))

(defn connect [db op-data state-id op-id op-time]
  (assoc-in db [:states state-id] (merge schema/state {:id state-id})))

(defn disconnect [db op-data state-id op-id op-time]
  (update db :states (fn [state] (dissoc state state-id))))
