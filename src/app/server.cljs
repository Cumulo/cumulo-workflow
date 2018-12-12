
(ns app.server
  (:require [app.schema :as schema]
            [app.service :refer [run-server! sync-clients!]]
            [app.updater :refer [updater]]
            [cljs.reader :refer [read-string]]
            [cumulo-reel.reel :refer [reel-reducer refresh-reel reel-schema]]
            ["fs" :as fs]
            ["shortid" :as shortid]
            ["child_process" :as cp]
            ["path" :as path]
            [app.node-config :as node-config]
            [app.node-config :refer [dev?]]
            [app.config :as config]
            [cumulo-util.file :refer (write-mildly!)]))

(def initial-db
  (let [filepath (:storage-path node-config/env)]
    (if (fs/existsSync filepath)
      (do
       (println "Found storage in:" (:storage-path node-config/env))
       (read-string (fs/readFileSync filepath "utf8")))
      schema/database)))

(defonce *reel (atom (merge reel-schema {:base initial-db, :db initial-db})))

(defonce *reader-reel (atom @*reel))

(defn persist-db! []
  (let [file-content (pr-str (assoc (:db @*reel) :sessions {}))
        now (js/Date.)
        storage-path (:storage-path node-config/env)
        backup-path (path/join
                     js/__dirname
                     "backups"
                     (str (inc (.getMonth now)))
                     (str (.getDate now) "-storage.edn"))]
    (write-mildly! storage-path file-content)
    (write-mildly! backup-path file-content)))

(defn dispatch! [op op-data sid]
  (let [op-id (.generate shortid), op-time (.valueOf (js/Date.))]
    (if dev? (println "Dispatch!" (str op) op-data sid))
    (try
     (cond
       (= op :effect/persist) (persist-db!)
       :else
         (let [new-reel (reel-reducer @*reel updater op op-data sid op-id op-time)]
           (reset! *reel new-reel)))
     (catch js/Error error (.error js/console error)))))

(defn on-exit! [code]
  (persist-db!)
  (comment println "exit code is:" (pr-str code))
  (.exit js/process))

(defn render-loop! []
  (if (not (identical? @*reader-reel @*reel))
    (do (reset! *reader-reel @*reel) (sync-clients! @*reader-reel)))
  (js/setTimeout render-loop! 200))

(defn main! []
  (run-server! #(dispatch! %1 %2 %3) (:port config/site))
  (render-loop!)
  (.on js/process "SIGINT" on-exit!)
  (js/setInterval #(persist-db!) (* 60 1000 10))
  (println "Server started."))

(defn reload! []
  (println "Code updated.")
  (reset! *reel (refresh-reel @*reel initial-db updater))
  (sync-clients! @*reader-reel))
