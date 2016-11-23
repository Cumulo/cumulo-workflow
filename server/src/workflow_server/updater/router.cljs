
(ns workflow-server.updater.router )

(defn change [db op-data state-id op-id op-time]
  (assoc-in db [:states state-id :router] op-data))
