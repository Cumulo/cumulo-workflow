
(ns app.connection
  (:require [cljs.reader :as reader] [recollect.patch :refer [patch-twig]]))

(defonce *global-ws (atom nil))

(defn send! [op op-data]
  (let [ws @*global-ws]
    (if (some? ws)
      (.send ws (pr-str [op op-data]))
      (.warn js/console "WebSocket at close state!"))))

(defn setup-socket! [*store configs]
  (let [ws-url (:url configs), ws (js/WebSocket. ws-url)]
    (reset! *global-ws ws)
    (set!
     ws.onopen
     (fn [event] (let [listener (:on-open! configs)] (if (fn? listener) (listener event)))))
    (set!
     ws.onclose
     (fn [event]
       (reset! *global-ws nil)
       (let [listener (:on-close! configs)] (if (fn? listener) (listener event)))))
    (set!
     ws.onmessage
     (fn [event]
       (let [changes (reader/read-string event.data)]
         (.log js/console "Changes" (clj->js changes))
         (reset! *store (patch-twig @*store changes)))))))
