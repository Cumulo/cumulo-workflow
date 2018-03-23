
(ns app.comp.reel
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo-ui.colors :as colors]
            [respo.macros :refer [defcomp action-> <> span button div]]
            [respo.comp.space :refer [=<]]))

(def style-click
  {:cursor :pointer, :color (hsl 200 80 80), :font-size :12, :text-decoration :underline})

(defcomp
 comp-reel
 (count addional-styles)
 (div
  {:style (merge
           {:padding 8,
            :position :absolute,
            :bottom 8,
            :right 8,
            :font-size 12,
            :color (hsl 0 0 60)}
           addional-styles)}
  (<> span (str "Length:" count) nil)
  (=< 8 nil)
  (span
   {:inner-text "Reset",
    :style style-click,
    :on {:click (fn [e d! m!] (d! :reel/reset nil))}})
  (=< 8 nil)
  (span
   {:inner-text "Merge",
    :style style-click,
    :on {:click (fn [e d! m!] (d! :reel/merge nil))}})
  (=< 8 nil)
  (span
   {:inner-text "Persist", :style style-click, :on {:click (action-> :effect/persist nil)}})))
