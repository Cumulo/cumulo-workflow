
(ns app.main
  (:require [respo.core :refer [render! clear-cache!]]
            [respo.cursor :refer [mutate]]
            [app.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [app.network :refer [send! setup-socket!]]
            [app.schema :as schema]))

(defonce store-ref (atom nil))

(defn dispatch! [op op-data]
  (cond
    (= op :states)
      (let [new-store (update @store-ref :states (mutate op-data))]
        (reset! store-ref new-store))
    :else (send! op op-data)))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch!)))

(defn on-jsload! [] (clear-cache!) (render-app!) (println "Code updated."))

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
  (println "App started!"))

(set! js/window.onload -main)
