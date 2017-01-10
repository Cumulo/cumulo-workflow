
(ns workflow-server.main
  (:require [cljs.nodejs :as nodejs]
            [workflow-server.schema :as schema]
            [workflow-server.network :refer [run-server! render-clients!]]
            [workflow-server.updater.core :refer [updater]]
            [cljs.core.async :refer [<!]]
            [cljs.reader :refer [read-string]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defonce writer-db-ref
  (atom
   (let [fs (js/require "fs"), filepath (:storage-key schema/configs)]
     (enable-console-print!)
     (if (fs.existsSync filepath)
       (do (println "Found storage.") (read-string (fs.readFileSync filepath "utf8")))
       (do (println "Found no storage.") schema/database)))))

(defonce reader-db-ref (atom @writer-db-ref))

(defn persist! []
  (let [fs (js/require "fs")]
    (fs.writeFileSync (:storage-key schema/configs) (pr-str @writer-db-ref))))

(defn render-loop! []
  (if (not= @reader-db-ref @writer-db-ref)
    (do
     (reset! reader-db-ref @writer-db-ref)
     (comment println "render loop")
     (render-clients! @reader-db-ref)))
  (js/setTimeout render-loop! 300))

(defn -main []
  (nodejs/enable-util-print!)
  (let [server-ch (run-server! {:port (:port schema/configs)})]
    (go-loop
     []
     (let [[op op-data session-id op-id op-time] (<! server-ch)]
       (println "Action:" op op-data session-id op-id op-time)
       (comment println "Database:" @writer-db-ref)
       (try
        (let [new-db (updater @writer-db-ref op op-data session-id op-id op-time)]
          (reset! writer-db-ref new-db))
        (catch js/Error e (.log js/console e)))
       (recur)))
    (render-loop!))
  (add-watch reader-db-ref :log (fn [] ))
  (.on js/process "exit" (fn [code] (println "Saving file on exit" code) (persist!)))
  (println "Server started."))

(defn rm-caches! []
  (.execSync (js/require "child_process") "rm .lumo_cache/workflow_server_SLASH_*"))

(defn on-jsload! [] (println "Code updated.") (render-clients! @reader-db-ref))

(set! *main-cli-fn* -main)
