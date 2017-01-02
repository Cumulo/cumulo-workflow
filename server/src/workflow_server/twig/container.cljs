
(ns workflow-server.twig.container
  (:require [recollect.bunch :refer [create-twig]]
            [workflow-server.twig.user :refer [twig-user]]))

(def twig-container
  (create-twig
   :container
   (fn [db state]
     (let [logged-in? (some? (:user-id state)), router (:router state)]
       (if logged-in?
         {:router router,
          :state state,
          :logged-in? true,
          :statistics {},
          :user (twig-user (get-in db [:users (:user-id state)]))}
         {:state state, :logged-in? false})))))
