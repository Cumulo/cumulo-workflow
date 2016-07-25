
(ns workflow.style.widget
  (:require [hsl.core :refer [hsl]]
            [workflow.style.layout :as layout]))

(def card {:padding "20px"})

(def resource
 {:line-height 2,
  :color (hsl 200 20 50),
  :font-size "14px",
  :padding "0 8px",
  :text-decoration "none"})

(def avatar
 {:width "80px",
  :flex-shrink 0,
  :background-image (str "url(tiye-400x400.jpg)"),
  :border-radius "50%",
  :background-size "cover",
  :height "80px"})

(def dim9 {:background-color (hsl 0 0 90)})

(def dim8 {:background-color (hsl 0 0 80)})

(def dim7 {:background-color (hsl 0 0 70)})

(def notice-large
 {:color (hsl 0 0 70), :font-size "28px", :font-weight "lighter"})

(def textbox
 {:line-height 2,
  :font-size "16px",
  :padding "0 8px",
  :outline "none",
  :border "none"})

(def button
 {:line-height 2,
  :color "white",
  :font-size "14px",
  :font-weight "normal",
  :background-color (hsl 200 60 80),
  :padding "0 8px",
  :outline "none",
  :border "none",
  :border-radius "8px"})

(def username
 {:line-height 2,
  :text-overflow "ellipsis",
  :color (hsl 0 0 60),
  :text-align "right",
  :font-size "12px",
  :overflow "hidden",
  :background-color (hsl 30 80 100),
  :width "100px",
  :padding "0 8px",
  :display "inline-block",
  :border-radius "4px"})

(def message {:padding "4px 0"})

(def row-divider {:background-color (hsl 0 0 94), :width "2px"})

(def time-tip
 {:color (hsl 0 0 80),
  :vertical-align "middle",
  :font-size "10px",
  :font-weight "lighter",
  :flex-shrink 0,
  :align-self "center",
  :font-family "Menlo,monospace"})

(def showcase
 (merge
   layout/row
   {:align-items "center",
    :width "180px",
    :padding "0 8px",
    :margin-right "8px",
    :border (str "1px solid " (hsl 0 0 90)),
    :border-radius "4px",
    :margin-bottom "8px",
    :height "40px"}))

(defn logo-small [img]
  {:width "24px",
   :background-image (str "url(" img ")"),
   :background-repeat "no-repeat",
   :background-position "center",
   :background-size "contain",
   :height "24px"})

(def number-highlight
 {:line-height 2,
  :color (hsl 0 0 100),
  :font-size "12px",
  :background-color (hsl 40 80 60),
  :padding "0 8px",
  :display "inline-block",
  :border-radius "12px"})
