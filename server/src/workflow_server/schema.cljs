
(ns workflow-server.schema )

(def user {:name nil, :nickname nil, :id nil, :avatar nil})

(def database {:states {}, :users {}})

(def state {:router nil, :nickname nil, :user-id nil, :id nil})

(def router {:router nil, :name nil, :title nil, :data {}})
