
(ns workflow-server.main
  (:require [cljs.nodejs :as nodejs]
            [workflow-server.schema :as schema]
            [cumulo-server.core :refer [setup-server! reload-renderer!]]
            [workflow-server.updater.core :refer [updater]]
            [workflow-server.view :refer [render-view render-scene]]))

(defonce db-ref (atom schema/database))

(defn -main []
  (nodejs/enable-util-print!)
  (setup-server! db-ref updater render-scene render-view {:port 5020})
  (add-watch db-ref :log (fn []))
  (println "server started"))

(set! *main-cli-fn* -main)

(defn on-jsload []
  (println "code updated")
  (reload-renderer! @db-ref updater render-scene render-view))
