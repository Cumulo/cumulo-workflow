
(ns server.util )

(defn find-first [f xs] (reduce (fn [_ x] (when (f x) (reduced x))) nil xs))

(defn try-verbosely! [x] (try x (catch js/Error e (.log js/console e))))

(defn log-js! [& args]
  (apply js/console.log (map (fn [x] (if (coll? x) (clj->js x) x)) args)))
