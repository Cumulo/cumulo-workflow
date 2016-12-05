
(ns workflow-server.schema )

(def user {:password nil, :name nil, :nickname nil, :id nil, :avatar nil})

(def database {:states {}, :topics {}, :users {}})

(def state
  {:router {:router nil, :name :home, :data nil},
   :nickname nil,
   :user-id nil,
   :notifications [],
   :id nil})

(def router {:router nil, :name nil, :title nil, :data {}})

(def notification {:id nil, :kind nil, :text nil})
