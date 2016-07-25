
(ns workflow.component.container
  (:require [hsl.core :refer [hsl]]
            [respo.alias :refer [create-comp div span]]
            [workflow.style.widget :as widget]
            [workflow.style.layout :as layout]
            [respo.component.debug :refer [comp-debug]]))

(defn render [store]
  (fn [state mutate!]
    (div
      {:style (merge layout/fullscreen layout/horizontal)}
      (div {:style widget/row-divider})
      (comp-debug (:state store) {:bottom 0, :left 0}))))

(def comp-container (create-comp :container render))
