
(ns workflow.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [workflow.style.widget :as widget]
            [workflow.style.layout :as layout]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-text]]))

(defn render [store]
  (fn [state mutate!]
    (div
      {:style (merge layout/fullscreen layout/horizontal)}
      (div
        {}
        (div
          {:style ui/button,
           :event {:click (fn [e dispatch!] (dispatch! :ping nil))}}
          (comp-text "ping" nil)))
      (comp-debug store {:bottom 0, :max-width "100%", :left 0}))))

(def comp-container (create-comp :container render))
