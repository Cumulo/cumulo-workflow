
(ns workflow.comp.topics
  (:require [respo.alias :refer [create-comp div span input button]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.space :refer [comp-space]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [workflow.comp.topic :refer [comp-topic]]))

(defn on-input [mutate!] (fn [e dispatch!] (mutate! (:value e))))

(defn on-add [mutate! text] (fn [e dispatch!] (dispatch! :topic/create text) (mutate! "")))

(defn update-state [state text] text)

(def style-header {:font-size 24, :font-weight :lighter, :font-family "Josefin Sans"})

(defn init-state [& args] "")

(defn render [topics logged-in?]
  (fn [state mutate!]
    (div
     {:style (merge ui/flex)}
     (div {:style style-header} (comp-text "Topics" nil))
     (if logged-in?
       (div
        {:style ui/row}
        (input
         {:style (merge ui/flex ui/input),
          :event {:input (on-input mutate!)},
          :attrs {:placeholder "Some topic...", :value state}})
        (comp-space 8 nil)
        (button
         {:style ui/button, :event {:click (on-add mutate! state)}}
         (comp-text "Add" nil))))
     (div {} (->> (vals topics) (map (fn [topic] [(:id topic) (comp-topic topic)])))))))

(def comp-topics (create-comp :topics init-state update-state render))
