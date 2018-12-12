
(ns app.service
  (:require [cljs.nodejs :as nodejs]
            [cljs.reader :as reader]
            [app.twig.container :refer [twig-container]]
            [recollect.diff :refer [diff-twig]]
            [recollect.twig :refer [render-twig]]
            [ws-edn.server :refer [wss-serve! wss-send!]]))

(defonce *registry (atom {}))

(defonce client-caches (atom {}))

(defn run-server! [on-action! port]
  (wss-serve!
   port
   {:on-open! (fn [sid socket]
      (on-action! :session/connect nil sid)
      (swap! *registry assoc sid socket)
      (.info js/console "New client.")),
    :on-data! (fn [sid action]
      (case (:kind action)
        :op (on-action! (:op action) (:data action) sid)
        (println "unknown data" action))),
    :on-close! (fn [sid event]
      (.warn js/console "Client closed!")
      (swap! *registry dissoc sid)
      (on-action! :session/disconnect nil sid)),
    :on-error! (fn [error] (.error js/console error))}))

(defn sync-clients! [reel]
  (let [db (:db reel), records (:records reel)]
    (doseq [sid (keys @*registry)]
      (let [sid sid
            session (get-in db [:sessions sid])
            old-store (or (get @client-caches sid) nil)
            new-store (render-twig (twig-container db session records) old-store)
            changes (diff-twig old-store new-store {:key :id})
            socket (get @*registry sid)]
        (println "Changes for" sid ":" changes (count records))
        (if (and (not= changes []) (some? socket))
          (do
           (.send socket (pr-str {:kind :patch, :data changes}))
           (swap! client-caches assoc sid new-store)))))))
