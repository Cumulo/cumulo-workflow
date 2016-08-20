
(ns workflow.style.layout
  (:require [hsl.core :refer [hsl]]))

(def scroll {:overflow "auto"})

(def article {:padding "40px", :overflow-y "auto"})

(def sidebar
 {:min-width "260px", :background-color (hsl 0 0 96), :flex 1})

(def vertical
 {:align-items "stretch",
  :justify-content "space-between",
  :display "flex",
  :flex-direction "column"})

(def horizontal-box
 {:align-items "center",
  :justify-content "center",
  :display "flex",
  :flex-direction "row"})

(def vertical-box
 {:align-items "center",
  :justify-content "center",
  :display "flex",
  :flex-direction "column"})

(def column
 {:align-items "stretch",
  :justify-content "flex-start",
  :display "flex",
  :flex-direction "column"})

(def row
 {:align-items "stretch",
  :justify-content "flex-start",
  :display "flex",
  :flex-direction "row"})

(def horizontal
 {:align-items "stretch",
  :justify-content "space-between",
  :display "flex",
  :flex-direction "row"})

(def fullscreen {:width "100%", :position "absolute", :height "100%"})

(def flex {:flex 1, :flex-shrink 0})
