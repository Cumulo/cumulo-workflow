
(ns workflow.comp.header
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]
            [respo.comp.space :refer [comp-space]]))

(defn on-profile [e dispatch!]
  (dispatch! :router/change {:router nil, :name :profile, :params nil}))

(def style-header
  {:color :white,
   :font-size 16,
   :background-color colors/motif,
   :padding "0 16px",
   :justify-content :space-between,
   :height 48})

(defn on-home [e dispatch!]
  (dispatch! :router/change {:router nil, :name :home, :params nil}))

(def style-logo {:cursor :pointer})

(def style-pointer {:cursor "pointer"})

(defn render [logged-in?]
  (fn [state mutate!]
    (div
     {:style (merge ui/row-center style-header)}
     (div {:style style-logo, :event {:click on-home}} (comp-text "Workflow" nil))
     (div
      {:style style-pointer, :event {:click on-profile}}
      (comp-text (if logged-in? "Me" "Guest") nil)))))

(def comp-header (create-comp :header render))
