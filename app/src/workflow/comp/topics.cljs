
(ns workflow.comp.topics
  (:require [respo.alias :refer [create-comp div]]
            [respo.comp.text :refer [comp-text]]
            [respo-ui.style :as ui]))

(def style-header {:font-size 16, :font-family "Josefin Sans"})

(defn render [topics]
  (fn [state mutate!]
    (div {:style ui/flex} (div {:style style-header} (comp-text "Topics" nil)))))

(def comp-topics (create-comp :topics render))
