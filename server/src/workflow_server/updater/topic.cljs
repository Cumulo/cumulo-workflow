
(ns workflow-server.updater.topic )

(defn add-one [db op-data state-id op-id op-time]
  (assoc-in
   db
   [:topics op-id]
   (let [state (get-in db [:states state-id])]
     {:time op-time, :title op-data, :messages {}, :id op-id, :author-id (:user-id state)})))
