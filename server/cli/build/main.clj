
(ns build.main
  (:require [shadow.cljs.devtools.api :as shadow]
            [clojure.java.shell :refer [sh]]))

(def configs {:name "cumulo-workflow"})

(defn sh! [command]
  (println command)
  (println (sh "bash" "-c" command)))

(defn watch []
  (shadow/watch :app))

(defn release []
  (shadow/release :app))

(defn upload []
  (sh! (str "rsync -avr --progress dist/* tiye.me:servers/" (:name configs))))
