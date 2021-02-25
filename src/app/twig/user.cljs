
(ns app.twig.user (:require ))

(defn twig-user [user] (dissoc user :password))
