
(ns workflow.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]
            [workflow.comp.home :refer [comp-home]]
            [respo-message.comp.msg-list :refer [comp-msg-list]]))

(def style-header
  {:color :white,
   :font-size 16,
   :background-color colors/motif,
   :padding "0 16px",
   :justify-content :space-between,
   :height 48})

(def style-body {})

(defn render [store]
  (fn [state mutate!]
    (div
     {:style (merge ui/global ui/fullscreen ui/column)}
     (div
      {:style (merge ui/row-center style-header)}
      (div {} (comp-text "Messages" nil))
      (div {:style {:cursor "pointer"}, :event {}} (comp-text "Not logged in" nil)))
     (div {:style style-body} (comp-home store))
     (comment comp-debug (:seeing-messages store) {:bottom 0, :max-width "100%", :left 0})
     (comp-msg-list (get-in store [:state :notifications]) :state/remove-notification))))

(def comp-container (create-comp :container render))
