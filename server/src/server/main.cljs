
(ns server.main
  (:require [server.schema :as schema]
            [server.network :refer [run-server! sync-clients!]]
            [server.updater.core :refer [updater]]
            [cljs.reader :refer [read-string]]
            [server.util :refer [try-verbosely! log-js!]]
            [server.reel :refer [reel-updater refresh-reel reel-schema]]
            ["fs" :as fs]))

(def initial-db
  (let [filepath (:storage-key schema/configs)]
    (if (fs/existsSync filepath)
      (do (println "Found storage.") (read-string (fs/readFileSync filepath "utf8")))
      schema/database)))

(defonce *reader-db (atom initial-db))

(defonce *reel (atom (merge reel-schema {:base initial-db, :db initial-db})))

(defn reload! []
  (println "Code updated.")
  (reset! *reel (refresh-reel @*reel initial-db updater))
  (sync-clients! @*reader-db))

(defn render-loop! []
  (if (not= @*reader-db (:db @*reel))
    (do (reset! *reader-db (:db @*reel)) (sync-clients! @*reader-db)))
  (js/setTimeout render-loop! 300))

(defn on-exit! [code]
  (fs/writeFileSync (:storage-key schema/configs) (pr-str (assoc (:db @*reel) :sessions {})))
  (println "Saving file on exit" code)
  (.exit js/process))

(defn dispatch! [op op-data sid op-id op-time]
  (log-js! "Action" (str op) op-data sid op-id op-time)
  (try-verbosely!
   (let [new-reel (reel-updater @*reel updater op op-data sid op-id op-time)]
     (reset! *reel new-reel))))

(defn main! []
  (run-server! dispatch! (:port schema/configs))
  (render-loop!)
  (.on js/process "SIGINT" on-exit!)
  (println "Server started."))
