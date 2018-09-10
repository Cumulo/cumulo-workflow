
(ns app.node-config (:require ["path" :as path] [app.config :as config]))

(def env {:storage-path (path/join js/__dirname (str (:storage-key config/site) ".edn"))})
