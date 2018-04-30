
(ns app.updater.page (:require [app.schema :as schema]))

(defn create [db op-data sid op-id op-time]
  (assoc-in db [:pages op-id] {:id op-id, :title op-data, :time op-time}))

(defn remove-one [db op-data sid op-id op-time]
  (update db :pages (fn [pages] (dissoc pages op-data))))

(defn update-title [db op-data sid op-id op-time]
  (let [page-id (:id op-data), title (:title op-data)]
    (assoc-in db [:pages page-id :title] title)))
