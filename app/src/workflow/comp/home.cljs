
(ns workflow.comp.home
  (:require [respo.alias :refer [create-comp div]]
            [respo.comp.text :refer [comp-text]]
            [respo-ui.style :as ui]
            [workflow.comp.topics :refer [comp-topics]]
            [workflow.comp.login :refer [comp-login]]))

(def style-container {:padding "8px 16px"})

(defn render [store]
  (fn [state mutate!]
    (div
     {:style (merge ui/row style-container)}
     (comp-topics store)
     (if (:logged-in? store)
       (div {:style ui/flex} (comp-text (str "Hello! " (get-in store [:user :name])) nil))
       (comp-login)))))

(def comp-home (create-comp :home render))
