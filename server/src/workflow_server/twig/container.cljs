
(ns workflow-server.twig.container (:require [recollect.bunch :refer [create-twig]]))

(def twig-container (create-twig :container (fn [db state] {:state state, :statistics {}})))
