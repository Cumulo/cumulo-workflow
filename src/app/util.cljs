
(ns app.util )

(defn find-first [f xs] (reduce (fn [_ x] (when (f x) (reduced x))) nil xs))

(defn try-verbosely! [x] (try x (catch js/Error e (.error js/console e))))
