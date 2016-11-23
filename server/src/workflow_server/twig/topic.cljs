
(ns workflow-server.twig.topic (:require [recollect.bunch :refer [create-twig]]))

(def twig-topic (create-twig :topic (fn [topic] (dissoc topic :messages))))
