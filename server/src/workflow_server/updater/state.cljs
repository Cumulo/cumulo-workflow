
(ns workflow-server.updater.state (:require [workflow-server.schema :as schema]))

(defn disconnect [db op-data state-id op-id op-time]
  (update db :states (fn [state] (dissoc state state-id))))

(defn connect [db op-data state-id op-id op-time]
  (assoc-in db [:states state-id] (merge schema/state {:id state-id})))
