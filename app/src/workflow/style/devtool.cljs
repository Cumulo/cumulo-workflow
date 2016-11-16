
(ns workflow.style.devtool (:require [hsl.core :refer [hsl]]))

(def shadow {:box-shadow (str "0 0 1px " (hsl 0 0 0 0.4))})

(def dim {:background-color (hsl 0 0 90)})
