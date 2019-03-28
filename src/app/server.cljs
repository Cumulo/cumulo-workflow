
(ns app.server
  (:require [app.schema :as schema]
            [app.updater :refer [updater]]
            [cljs.reader :refer [read-string]]
            [cumulo-reel.core :refer [reel-reducer refresh-reel reel-schema]]
            ["fs" :as fs]
            ["path" :as path]
            [app.config :as config]
            [cumulo-util.file :refer [write-mildly! get-backup-path! merge-local-edn!]]
            [cumulo-util.core :refer [id! repeat! unix-time! delay!]]
            [app.twig.container :refer [twig-container]]
            [recollect.diff :refer [diff-twig]]
            [recollect.twig :refer [render-twig]]
            [ws-edn.server :refer [wss-serve! wss-send! wss-each!]])
  (:require-macros [clojure.core.strint :refer [<<]]))

(defonce *client-caches (atom {}))

(def storage-file (path/join js/__dirname (:storage-file config/site)))

(defonce initial-db
  (merge-local-edn!
   schema/database
   storage-file
   (fn [found?] (if found? (println "Found local EDN data") (println "Found no data")))))

(defonce *reel (atom (merge reel-schema {:base initial-db, :db initial-db})))

(defonce *reader-reel (atom @*reel))

(defn persist-db! []
  (let [file-content (pr-str (assoc (:db @*reel) :sessions {}))
        storage-path storage-file
        backup-path (get-backup-path!)]
    (write-mildly! storage-path file-content)
    (write-mildly! backup-path file-content)))

(defn dispatch! [op op-data sid]
  (let [op-id (id!), op-time (unix-time!)]
    (if config/dev? (println "Dispatch!" (str op) op-data sid))
    (try
     (cond
       (= op :effect/persist) (persist-db!)
       :else (reset! *reel (reel-reducer @*reel updater op op-data sid op-id op-time)))
     (catch js/Error error (js/console.error error)))))

(defn on-exit! [code]
  (persist-db!)
  (comment println "exit code is:" (pr-str code))
  (js/process.exit))

(defn sync-clients! [reel]
  (wss-each!
   (fn [sid socket]
     (let [db (:db reel)
           records (:records reel)
           session (get-in db [:sessions sid])
           old-store (or (get @*client-caches sid) nil)
           new-store (render-twig (twig-container db session records) old-store)
           changes (diff-twig old-store new-store {:key :id})]
       (when config/dev? (println "Changes for" sid ":" changes (count records)))
       (if (not= changes [])
         (do
          (wss-send! sid {:kind :patch, :data changes})
          (swap! *client-caches assoc sid new-store)))))))

(defn render-loop! []
  (when (not (identical? @*reader-reel @*reel))
    (reset! *reader-reel @*reel)
    (sync-clients! @*reader-reel))
  (delay! 0.2 render-loop!))

(defn run-server! [port]
  (wss-serve!
   port
   {:on-open (fn [sid socket]
      (dispatch! :session/connect nil sid)
      (js/console.info "New client.")),
    :on-data (fn [sid action]
      (case (:kind action)
        :op (dispatch! (:op action) (:data action) sid)
        (println "unknown data" action))),
    :on-close (fn [sid event]
      (js/console.warn "Client closed!")
      (dispatch! :session/disconnect nil sid)),
    :on-error (fn [error] (.error js/console error))}))

(defn main! []
  (println "Running mode:" (if config/dev? "dev" "release"))
  (let [port (if (some? js/process.env.port)
               (js/parseInt js/process.env.port)
               (:port config/site))]
    (run-server! port)
    (println (<< "Server started on port ~{port}.")))
  (render-loop!)
  (js/process.on "SIGINT" on-exit!)
  (repeat! 600 #(persist-db!)))

(defn reload! []
  (println "Code updated.")
  (reset! *reel (refresh-reel @*reel initial-db updater))
  (sync-clients! @*reader-reel))
