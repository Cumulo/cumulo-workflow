
(ns workflow-server.watcher
  (:require [cljs.js :as cljs]
            [clojure.string :as string]
            [workflow-server.main :as main]))

(def gaze (js/require "gaze"))
(def path (js/require "path"))
(def vm (js/require "vm"))
(def cp (js/require "child_process"))

(defn node-eval [{:keys [name source]}]
  (.runInThisContext vm source (str (munge name) ".js")))

(defn eval-inside! [st code]
  )

(defn handle-reload! [ns-path]
  (let [st (cljs/empty-state)
        segment (-> ns-path
                    (string/replace "-" "_")
                    (string/replace "." "_SLASH_"))]
    (cp.execSync (str "rm -f .lumo_cache/" segment "*"))
    (cljs/eval st
      `(~'require (quote ~(symbol ns-path)) :reload)
      {:eval node-eval :load @#'lumo.repl/load}
      (fn [error]
        (println "Reload namespace:" ns-path)
        (println error)))
    (main/on-jsload!)))

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
