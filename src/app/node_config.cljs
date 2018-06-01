
(ns app.node-config (:require ["path" :as path]))

(def env {:storage-path (path/join js/__dirname "workflow-storage.edn")})
