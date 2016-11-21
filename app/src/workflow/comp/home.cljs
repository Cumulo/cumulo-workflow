
(ns workflow.comp.home
  (:require [respo.alias :refer [create-comp div]]
            [respo.comp.text :refer [comp-text]]
            [respo-ui.style :as ui]
            [workflow.comp.topics :refer [comp-topics]]
            [workflow.comp.login :refer [comp-login]]))

(def style-container {})

(defn render [store]
  (fn [state mutate!]
    (div {:style (merge ui/row style-container)} (comp-topics store) (comp-login))))

(def comp-home (create-comp :home render))
