
(ns app.build-info (:require ["node-notifier" :as notifier] [clojure.string :as string]))

(defn on-build! [status warning]
  (case (:type status)
    :build-start (do)
    :build-init (do)
    :build-failure
      (.notify
       notifier
       (clj->js
        {:title "Failure",
         :message (:report status),
         :type "error",
         :sound true,
         :time 10000}))
    :build-complete
      (let [warnings (->> status
                          :info
                          :sources
                          (map :warnings)
                          (filter (fn [xs] (not (empty? xs)))))]
        (if (> (count warnings) 0)
          (.notify
           notifier
           (clj->js
            {:title "Build",
             :message (->> warnings
                           (apply concat)
                           (map
                            (fn [warning] (str (:resource-name warning) " " (:msg warning))))
                           (string/join "\n")),
             :sound true,
             :type "warn"}))
          (.notify
           notifier
           (clj->js
            {:title (pr-str (:build-id status)), :message "OK", :time 400, :type "info"}))))
    (do (println "Unknown:" (:type status)))))
