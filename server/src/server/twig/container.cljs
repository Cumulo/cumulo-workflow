
(ns server.twig.container
  (:require [recollect.bunch :refer [create-twig]] [server.twig.user :refer [twig-user]]))

(def twig-container
  (create-twig
   :container
   (fn [db session records]
     (let [logged-in? (some? (:user-id session))
           router (:router session)
           base-data {:logged-in? logged-in?,
                      :session session,
                      :count (:count db),
                      :reel-length (count records)}]
       (merge
        base-data
        (if logged-in?
          {:user (twig-user (get-in db [:users (:user-id session)])), :router router}
          nil))))))
