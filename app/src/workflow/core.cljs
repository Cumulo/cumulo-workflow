
(ns workflow.core
  (:require [respo.core :refer [render! clear-cache!]]
            [workflow.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [cumulo-client.core :refer [send! setup-socket!]]))

(defn dispatch! [op op-data] (send! op op-data))

(defonce store-ref (atom {}))

(defonce states-ref (atom {}))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "code updated."))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (setup-socket!
    store-ref
    {:on-close! (fn [event] (.error js/console "Lost connection!")),
     :url "ws://repo:5020"})
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (println "app started!")
  (let [configEl (.querySelector js/document "#config")
        config (read-string (.-innerHTML configEl))]
    (if (and (some? navigator.serviceWorker) (:build? config))
      (-> navigator.serviceWorker
       (.register "./sw.js")
       (.then
         (fn [registration]
           (println "resigtered:" registration.scope)))
       (.catch (fn [error] (println "failed:" error)))))))

(set! js/window.onload -main)
