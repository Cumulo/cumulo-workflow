
(ns server.network
  (:require [cljs.nodejs :as nodejs]
            [cljs.reader :as reader]
            [server.twig.container :refer [twig-container]]
            [recollect.diff :refer [diff-bunch]]
            [recollect.bunch :refer [render-bunch]]
            [server.util :refer [log-js!]]
            ["shortid" :as shortid]
            ["ws" :as ws]))

(defonce socket-registry (atom {}))

(defn run-server! [on-action! port]
  (let [WebSocketServer (.-Server ws), wss (new WebSocketServer (js-obj "port" port))]
    (.on
     wss
     "connection"
     (fn [socket]
       (let [sid (.generate shortid)
             op-id (.generate shortid)
             op-time (.valueOf (js/Date.))]
         (on-action! :session/connect nil sid op-id op-time)
         (swap! socket-registry assoc sid socket)
         (.info js/console "New client.")
         (.on
          socket
          "message"
          (fn [rawData]
            (let [action (reader/read-string rawData), [op op-data] action]
              (on-action! op op-data sid op-id op-time))))
         (.on
          socket
          "close"
          (fn []
            (.warn js/console "Client closed!")
            (swap! socket-registry dissoc sid)
            (on-action! :session/disconnect nil sid op-id op-time))))))))

(defonce client-caches (atom {}))

(defn sync-clients! [db]
  (doseq [session-entry (:sessions db)]
    (let [[session-id session] session-entry
          old-store (or (get @client-caches session-id) nil)
          new-store (render-bunch (twig-container db session) old-store)
          changes (diff-bunch old-store new-store {:key :id})
          socket (get @socket-registry session-id)]
      (log-js! "Changes for" session-id ":" changes)
      (if (and (not= changes []) (some? socket))
        (do (.send socket (pr-str changes)) (swap! client-caches assoc session-id new-store))))))
