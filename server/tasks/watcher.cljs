
(ns workflow-server.watcher
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
  (println "Trying to reload:" ns-path)
  (let [st (cljs/empty-state)
        segment (-> ns-path
                    (string/replace "-" "_")
                    (string/replace "." "_SLASH_"))]
    (cp.execSync (str "rm -fv .lumo_cache/" segment "*"))
    (.write client (str "(require '" ns-path " :reload)" \newline))
    (.write client "(require '[workflow-server.main :as main])\n")
    (.write client "(main/on-jsload!)\n")))

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
