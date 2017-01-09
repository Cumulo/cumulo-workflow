
(ns workflow-server.twig.container
  (:require [recollect.bunch :refer [create-twig]]
            [workflow-server.twig.user :refer [twig-user]]))

(def twig-container
  (create-twig
   :container
   (fn [db session]
     (let [logged-in? (some? (:user-id session)), router (:router session)]
       (if logged-in?
         {:router router,
          :logged-in? true,
          :statistics {},
          :user (twig-user (get-in db [:users (:user-id session)])),
          :session session}
         {:logged-in? false, :session session})))))
