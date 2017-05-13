
(ns server.watcher
  (:require [cljs.js :as cljs]
            [clojure.string :as string]))

(def gaze (js/require "gaze"))
(def path (js/require "path"))
(def cp (js/require "child_process"))
(def net (js/require "net"))

(def client (.createConnection net (clj->js {:port 6000})))

(.on client "data"
  (fn [chunk]
    (.log js/console (.toString chunk))))

(.on client "end"
  (fn []
    (.log js/console "Connection ended!")
    (.exit js/process 0)))

(defn handle-reload! [ns-path]
  (let [reload-code (str "(require '" ns-path " :reload)\n"
                         "(require '[server.main :as main])\n"
                         "(main/on-jsload!)\n")]
    (println reload-code)
    (.write client reload-code)))

(defn handle-path! [filepath]
  (let
    [relative-path (path.relative (str js/process.env.PWD "/src") filepath)
     ns-path (-> relative-path
                (string/replace ".cljs" "")
                (string/replace "/" ".")
                (string/replace "_" "-"))]
    (handle-reload! ns-path)))

(defn watch-src! []
  (gaze "src/**/*.cljs"
    (fn [err, watcher]
      (.on watcher "changed"
        (fn ([filepath]
          (handle-path! filepath))))))
  (println "Started watching."))

(watch-src!)
