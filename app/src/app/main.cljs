
(ns app.main
  (:require [respo.core :refer [render! clear-cache! falsify-stage! render-element]]
            [respo.cursor :refer [mutate]]
            [app.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [app.network :refer [send! setup-socket!]]
            [app.schema :as schema]))

(defonce ref-store (atom nil))

(defn dispatch! [op op-data]
  (cond
    (= op :states)
      (let [new-store (update @ref-store :states (mutate op-data))]
        (reset! ref-store new-store))
    :else (send! op op-data)))

(def mount-target (.querySelector js/document "#app"))

(defn render-app! []
  (println "Calling render-app!")
  (render! (comp-container @ref-store) mount-target dispatch!))

(defn on-jsload! [] (clear-cache!) (render-app!) (println "Code updated."))

(defn simulate-login! []
  (let [raw (.getItem js/localStorage (:storage-key schema/configs))]
    (if (some? raw)
      (do (println "Found storage.") (dispatch! :user/log-in (read-string raw)))
      (do (println "Found no storage.")))))

(def server-rendered? (some? (.querySelector js/document "meta#server-rendered")))

(defn -main []
  (enable-console-print!)
  (println "Loaded")
  (if server-rendered?
    (falsify-stage! mount-target (render-element (comp-container @ref-store)) dispatch!))
  (render-app!)
  (setup-socket!
   ref-store
   {:url (str "ws://" (.-hostname js/location) ":" (:port schema/configs)),
    :on-close! (fn [event] (reset! ref-store nil) (.error js/console "Lost connection!")),
    :on-open! (fn [event] (simulate-login!))})
  (add-watch ref-store :changes render-app!)
  (println "App started!"))

(set! js/window.onload -main)
