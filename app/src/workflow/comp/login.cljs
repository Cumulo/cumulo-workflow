
(ns workflow.comp.login
  (:require [respo.alias :refer [create-comp div input button]]
            [respo.comp.text :refer [comp-text]]
            [respo-ui.style :as ui]))

(def style-title {})

(defn render [store]
  (fn [state mutate!]
    (div
     {}
     (div {:style style-title} (comp-text "Login" nil))
     (div {} (div {} (input {:style ui/input})) (div {} (input {:style ui/input})))
     (div
      {}
      (button
       {:style (merge ui/button {:outline :none, :border :none})}
       (comp-text "Submit" nil))))))

(def comp-login (create-comp :login render))
