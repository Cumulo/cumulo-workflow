
(ns app.client
  (:require [respo.core :refer [render! clear-cache! realize-ssr!]]
            [respo.cursor :refer [mutate]]
            [app.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [app.schema :as schema]
            [app.config :as config]
            [ws-edn.client :refer [ws-connect! ws-send!]]
            [recollect.patch :refer [patch-twig]]
            [cumulo-util.core :refer [on-page-touch]])
  (:require-macros [clojure.core.strint :refer [<<]]))

(declare dispatch!)

(declare connect!)

(declare simulate-login!)

(defonce *states (atom {}))

(defonce *store (atom nil))

(defn simulate-login! []
  (let [raw (.getItem js/localStorage (:storage-key config/site))]
    (if (some? raw)
      (do (println "Found storage.") (dispatch! :user/log-in (read-string raw)))
      (do (println "Found no storage.")))))

(defn dispatch! [op op-data]
  (when (and config/dev? (not= op :states)) (println "Dispatch" op op-data))
  (case op
    :states (reset! *states ((mutate op-data) @*states))
    :effect/connect (connect!)
    (ws-send! {:kind :op, :op op, :data op-data})))

(defn connect! []
  (ws-connect!
   (<< "ws://~{js/location.hostname}:~(:port config/site)")
   {:on-open (fn [] (simulate-login!)),
    :on-close (fn [event] (reset! *store nil) (js/console.error "Lost connection!")),
    :on-data (fn [data]
      (case (:kind data)
        :patch
          (let [changes (:data data)]
            (js/console.log "Changes" (clj->js changes))
            (reset! *store (patch-twig @*store changes)))
        (println "unknown kind:" data)))}))

(def mount-target (.querySelector js/document ".app"))

(defn render-app! [renderer]
  (renderer mount-target (comp-container @*states @*store) dispatch!))

(def ssr? (some? (.querySelector js/document "meta.respo-ssr")))

(defn main! []
  (println "Running mode:" (if config/dev? "dev" "release"))
  (if ssr? (render-app! realize-ssr!))
  (render-app! render!)
  (connect!)
  (add-watch *store :changes #(render-app! render!))
  (add-watch *states :changes #(render-app! render!))
  (on-page-touch #(if (nil? @*store) (connect!)))
  (println "App started!"))

(defn reload! [] (clear-cache!) (render-app! render!) (println "Code updated."))
