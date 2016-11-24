
(ns workflow.comp.topic
  (:require [respo.alias :refer [create-comp div span input button]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.debug :refer [comp-debug]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]))

(defn on-topic [topic-id]
  (fn [e dispatch!]
    (dispatch!
     :router/change
     {:router nil, :name :topic, :title (str "Topic:" topic-id), :data topic-id})))

(def style-topic {:font-size 16, :cursor :pointer})

(defn render [topic]
  (fn [state mutate!]
    (div
     {:style style-topic, :event {:click (on-topic (:id topic))}}
     (comment comp-debug topic nil)
     (comp-text (:title topic) nil))))

(def comp-topic (create-comp :topic render))
