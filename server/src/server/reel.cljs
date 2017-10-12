
(ns server.reel (:require [clojure.string :as string]))

(defn reel-updater [reel updater op op-data sid op-id op-time]
  (if (string/starts-with? (str op) ":reel/")
    (merge
     reel
     (case op
       :reel/reset {:records [], :db (:base reel)}
       :reel/merge {:records [], :base (:db reel)}
       (do (println "Unknown op:" op) reel)))
    (let [msg-pack [op op-data sid op-id op-time]]
      (-> reel
          (update :records (fn [records] (conj records msg-pack)))
          (assoc :db (updater (:db reel) op op-data sid op-id op-time))))))

(defn play-records [db records updater]
  (if (empty? records)
    db
    (let [[op op-data sid op-id op-time] (first records)
          next-db (updater db op op-data sid op-id op-time)]
      (recur next-db (rest records) updater))))

(defn refresh-reel [reel base updater]
  (let [next-base (if (:merged? reel) (:base reel) base)]
    (-> reel
        (assoc :base next-base)
        (assoc :db (play-records next-base (:records reel) updater)))))

(def reel-schema {:base nil, :db nil, :records [], :merged? false})
