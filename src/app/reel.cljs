
(ns app.reel (:require [clojure.string :as string] [app.schema :refer [dev?]]))

(defn play-records [db records updater]
  (if (empty? records)
    db
    (let [[op op-data sid op-id op-time] (first records)
          next-db (updater db op op-data sid op-id op-time)]
      (recur next-db (rest records) updater))))

(defn reel-reducer [reel updater op op-data sid op-id op-time]
  (if (string/starts-with? (str op) ":reel/")
    (merge
     reel
     (case op
       :reel/reset {:records [], :db (:base reel)}
       :reel/merge {:records [], :base (:db reel), :merged? true}
       (do (println "Unknown op:" op) reel)))
    (let [msg-pack [op op-data sid op-id op-time]]
      (-> reel
          (update :records (fn [records] (if dev? (conj records msg-pack) records)))
          (assoc :db (updater (:db reel) op op-data sid op-id op-time))))))

(def reel-schema {:base nil, :db nil, :records [], :merged? false})

(defn refresh-reel [reel base updater]
  (let [next-base (if (:merged? reel) (:base reel) base)]
    (-> reel
        (assoc :base next-base)
        (assoc :db (play-records next-base (:records reel) updater)))))
