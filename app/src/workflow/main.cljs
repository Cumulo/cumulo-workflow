
(ns workflow.main
  (:require [respo.core :refer [render! clear-cache!]]
            [workflow.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [workflow.network :refer [send! setup-socket!]]
            [workflow.schema :as schema]))

(defn dispatch! [op op-data] (send! op op-data))

(defonce store-ref (atom nil))

(defonce states-ref (atom {}))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(defn on-jsload! [] (clear-cache!) (render-app!) (println "code updated."))

(defn simulate-login! []
  (let [raw (.getItem js/localStorage (:storage-key schema/configs))]
    (if (some? raw)
      (do (println "Found storage.") (dispatch! :user/log-in (read-string raw)))
      (do (println "Found no storage.")))))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (setup-socket!
   store-ref
   {:url (str "ws://" (.-hostname js/location) ":" (:port schema/configs)),
    :on-close! (fn [event] (reset! store-ref nil) (.error js/console "Lost connection!")),
    :on-open! (fn [event] (simulate-login!))})
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (println "App started!"))

(set! js/window.onload -main)
