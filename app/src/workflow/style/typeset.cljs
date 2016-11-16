
(ns workflow.style.typeset (:require [hsl.core :refer [hsl]]))

(def entry {})

(def heading
  {:line-height 2,
   :color (hsl 0 0 70),
   :font-size "20px",
   :font-weight "lighter",
   :margin-top "16px"})

(def paragraph {:line-height 2})

(def title {:line-height 2, :font-size "16px", :font-weight "bold"})

(def description {:line-height 2, :color (hsl 0 0 60), :font-size 16})
