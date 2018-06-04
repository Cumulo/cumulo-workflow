
(ns app.util )

(defn find-first [f xs] (reduce (fn [_ x] (when (f x) (reduced x))) nil xs))

(defn get-env! [property] (aget (.-env js/process) property))
