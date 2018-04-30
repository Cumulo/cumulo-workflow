
(ns app.updater
  (:require [app.updater.session :as session]
            [app.updater.user :as user]
            [app.updater.router :as router]
            [app.updater.page :as page]))

(defn updater [db op op-data sid op-id op-time]
  (let [f (case op
            :session/connect session/connect
            :session/disconnect session/disconnect
            :user/log-in user/log-in
            :user/sign-up user/sign-up
            :user/log-out user/log-out
            :session/remove-notification session/remove-notification
            :router/change router/change
            :page/create page/create
            :page/update-title page/update-title
            :page/remove-one page/remove-one
            (do (println "Unknown op:" op) identity))]
    (f db op-data sid op-id op-time)))
