
(ns app.comp.login
  (:require [respo.core :refer [defcomp <> div input button span]]
            [respo.comp.space :refer [=<]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo-ui.core :as ui]
            [app.schema :as schema]
            [app.config :as config]))

(def initial-state {:username "", :password ""})

(defn on-submit [username password signup?]
  (fn [e dispatch!]
    (dispatch! (if signup? :user/sign-up :user/log-in) [username password])
    (.setItem js/localStorage (:storage-key config/site) [username password])))

(defcomp
 comp-login
 (states)
 (let [cursor (:cursor states), state (or (:data states) initial-state)]
   (div
    {:style (merge ui/flex ui/center)}
    (div
     {}
     (div
      {:style {}}
      (div
       {}
       (input
        {:placeholder "Username",
         :value (:username state),
         :style ui/input,
         :on-input (fn [e d!] (d! cursor (assoc state :username (:value e))))}))
      (=< nil 8)
      (div
       {}
       (input
        {:placeholder "Password",
         :value (:password state),
         :style ui/input,
         :on-input (fn [e d!] (d! cursor (assoc state :password (:value e))))})))
     (=< nil 8)
     (div
      {:style {:text-align :right}}
      (span
       {:inner-text "Sign up",
        :style (merge ui/link),
        :on-click (on-submit (:username state) (:password state) true)})
      (=< 8 nil)
      (span
       {:inner-text "Log in",
        :style (merge ui/link),
        :on-click (on-submit (:username state) (:password state) false)}))))))
