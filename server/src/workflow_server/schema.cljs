
(ns workflow-server.schema )

(def configs {:port 5021, :storage-key "/tmp/workflow-storage.edn"})

(def user {:password nil, :name nil, :nickname nil, :id nil, :avatar nil})

(def database {:topics {}, :sessions {}, :users {}})

(def router {:router nil, :name nil, :title nil, :data {}})

(def session
  {:router {:router nil, :name :home, :data nil},
   :nickname nil,
   :user-id nil,
   :notifications [],
   :id nil})

(def notification {:id nil, :kind nil, :text nil})
