
(ns app.updater.router )

(defn change [db op-data sid op-id op-time] (assoc-in db [:sessions sid :router] op-data))
