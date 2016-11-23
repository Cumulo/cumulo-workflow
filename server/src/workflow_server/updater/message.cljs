
(ns workflow-server.updater.message )

(defn create [db op-data state-id op-id op-time]
  (let [[topic-id text] op-data]
    (assoc-in
     db
     [:topics topic-id :messages op-id]
     {:time op-time,
      :id op-id,
      :topic-id topic-id,
      :author-id (get-in db [:states state-id :user-id]),
      :text text})))
