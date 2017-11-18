
(ns server.network
  (:require [cljs.nodejs :as nodejs]
            [cljs.reader :as reader]
            [server.twig.container :refer [twig-container]]
            [recollect.diff :refer [diff-twig]]
            [recollect.twig :refer [render-twig]]
            [server.util :refer [log-js!]]
            ["shortid" :as shortid]
            ["ws" :as ws]))

(defonce *registry (atom {}))

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
         (swap! *registry assoc sid socket)
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
            (swap! *registry dissoc sid)
            (on-action! :session/disconnect nil sid op-id op-time))))))))

(defonce client-caches (atom {}))

(defn sync-clients! [reel]
  (let [db (:db reel), records (:records reel)]
    (doseq [sid (keys @*registry)]
      (let [session-id sid
            session (get-in db [:sessions sid])
            old-store (or (get @client-caches session-id) nil)
            new-store (render-twig (twig-container db session records) old-store)
            changes (diff-twig old-store new-store {:key :id})
            socket (get @*registry session-id)]
        (log-js! "Changes for" session-id ":" changes (count records))
        (if (and (not= changes []) (some? socket))
          (do
           (.send socket (pr-str changes))
           (swap! client-caches assoc session-id new-store)))))))
