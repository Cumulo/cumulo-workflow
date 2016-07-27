
(ns workflow-server.view
  (:require [workflow-server.schema :as schema]))

(defn render-scene [db] db)

(defn render-view [state-id db]
  {:state (get-in db [:states state-id]), :statistics {}})
