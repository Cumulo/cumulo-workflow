
(ns workflow-server.schema )

(def message {:id nil, :topic-id nil, :author-id nil, :text nil})

(def user {:name nil, :nickname nil, :id nil, :avatar nil})

(def database {:states {}, :users {}})

(def state {:router nil, :nickname nil, :user-id nil, :id nil})

(def router {:router nil, :name nil, :title nil, :data {}})

(def topic {:time nil, :title nil, :messages {}, :id nil, :author-id nil})
