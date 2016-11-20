
(ns workflow-server.twig.container (:require [recollect.bunch :refer [create-twig]]))

(defn render [db state] db)

(def twig-container (create-twig :container render))
