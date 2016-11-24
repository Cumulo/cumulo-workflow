
(ns workflow.comp.room
  (:require [hsl.core :refer [hsl]]
            [clojure.string :as string]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span input button]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]
            [respo.comp.space :refer [comp-space]]))

(defn on-input [mutate!] (fn [e dispatch!] (mutate! (:value e))))

(defn update-state [state text] text)

(defn on-send [mutate! topic-id text]
  (fn [e dispatch!] (dispatch! :message/create [topic-id text]) (mutate! "")))

(defn init-state [& args] "")

(defn on-keydown [mutate! topic-id text]
  (fn [e dispatch!]
    (println e)
    (if (and (= (:key-code e) 13) (not (string/blank? text)))
      (do (dispatch! :message/create [topic-id text]) (mutate! "")))))

(defn render [messages topic-id]
  (fn [state mutate!]
    (div
     {:style (merge ui/column ui/flex)}
     (comp-text "Messages" nil)
     (div
      {:style ui/flex}
      (->> (vals messages)
           (map (fn [message] [(:id message) (div {} (comp-text (:text message) nil))]))))
     (div
      {:style ui/row}
      (input
       {:style (merge ui/input ui/flex),
        :event {:keydown (on-keydown mutate! topic-id state), :input (on-input mutate!)},
        :attrs {:placeholder "reply here...", :value state}})
      (comp-space 8 nil)
      (button
       {:style ui/button, :event {:click (on-send mutate! topic-id state)}}
       (comp-text "Send" nil))))))

(def comp-room (create-comp :room init-state update-state render))
