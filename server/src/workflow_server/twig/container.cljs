
(ns workflow-server.twig.container
  (:require [recollect.bunch :refer [create-twig]]
            [workflow-server.twig.user :refer [twig-user]]
            [workflow-server.twig.topic :refer [twig-topic]]))

(def twig-container
  (create-twig
   :container
   (fn [db state]
     (let [logged-in? (some? (:user-id state)), router (:router state)]
       {:state state,
        :logged-in? logged-in?,
        :statistics {},
        :topics (->> (:topics db)
                     (map (fn [entry] (let [[k v] entry] [k (twig-topic v)])))
                     (into {})),
        :seeing-messages (if (and logged-in? (= (:name router) :topic))
          (get-in db [:topics (:data router) :messages])
          nil),
        :user (if logged-in? (twig-user (get-in db [:users (:user-id state)])) nil)}))))
