
(ns server.main
  (:require [server.schema :as schema]
            [server.network :refer [run-server! sync-clients!]]
            [server.updater.core :refer [updater]]
            [cljs.reader :refer [read-string]]
            [server.util :refer [try-verbosely! log-js!]]
            [server.reel :refer [reel-updater refresh-reel reel-schema]]
            ["fs" :as fs]
            ["shortid" :as shortid]))

(def initial-db
  (let [filepath (:storage-key schema/configs)]
    (if (fs/existsSync filepath)
      (do (println "Found storage.") (read-string (fs/readFileSync filepath "utf8")))
      schema/database)))

(defonce *reel (atom (merge reel-schema {:base initial-db, :db initial-db})))

(defn dispatch! [op op-data sid]
  (let [op-id (.generate shortid), op-time (.valueOf (js/Date.))]
    (log-js! "Dispatch!" (str op) op-data sid)
    (try-verbosely!
     (let [new-reel (reel-updater @*reel updater op op-data sid op-id op-time)]
       (reset! *reel new-reel)))))

(defn on-exit! [code]
  (fs/writeFileSync (:storage-key schema/configs) (pr-str (assoc (:db @*reel) :sessions {})))
  (println "Saving file on exit" code)
  (.exit js/process))

(defn proxy-dispatch! [& args] "Make dispatch hot relodable." (apply dispatch! args))

(defonce *reader-reel (atom @*reel))

(defn render-loop! []
  (if (not (identical? @*reader-reel @*reel))
    (do (reset! *reader-reel @*reel) (sync-clients! @*reader-reel)))
  (js/setTimeout render-loop! 300))

(defn main! []
  (run-server! proxy-dispatch! (:port schema/configs))
  (render-loop!)
  (.on js/process "SIGINT" on-exit!)
  (println "Server started."))

(defn reload! []
  (println "Code updated.")
  (reset! *reel (refresh-reel @*reel initial-db updater))
  (sync-clients! @*reader-reel))
