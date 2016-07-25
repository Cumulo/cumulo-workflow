
(ns workflow-server.schema)

(def database {:states {}, :messages {}, :users {}})

(def state
 {:user-id nil,
  :buffer-time nil,
  :id nil,
  :show-settings? false,
  :buffer nil})

(def message
 {:writing? false, :time nil, :nickname "", :id nil, :text ""})
