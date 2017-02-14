
(ns client.comp.header
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]
            [respo.comp.space :refer [comp-space]]))

(defn on-profile [e dispatch!]
  (dispatch! :router/change {:name :profile, :params nil, :router nil}))

(def style-logo {:cursor :pointer})

(def style-pointer {:cursor "pointer"})

(def style-header
  {:height 48,
   :background-color colors/motif,
   :justify-content :space-between,
   :padding "0 16px",
   :font-size 16,
   :color :white})

(defn on-home [e dispatch!]
  (dispatch! :router/change {:name :home, :params nil, :router nil}))

(defn render [logged-in?]
  (fn [state mutate!]
    (div
     {:style (merge ui/row-center style-header)}
     (div {:event {:click on-home}, :style style-logo} (comp-text "Workflow" nil))
     (div
      {:style style-pointer, :event {:click on-profile}}
      (comp-text (if logged-in? "Me" "Guest") nil)))))

(def comp-header (create-comp :header render))
