
(ns app.comp.login
  (:require [respo.macros :refer [defcomp <> div input button span]]
            [respo.comp.space :refer [=<]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo-ui.core :as ui]
            [app.schema :as schema]
            [app.style :as style]))

(def initial-state {:username "", :password ""})

(defn on-input [state k] (fn [e dispatch! mutate!] (mutate! (assoc state k (:value e)))))

(defn on-submit [username password signup?]
  (fn [e dispatch!]
    (dispatch! (if signup? :user/sign-up :user/log-in) [username password])
    (.setItem js/localStorage (:storage-key schema/configs) [username password])))

(defcomp
 comp-login
 (states)
 (let [state (or (:data states) initial-state)]
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
         :on-input (on-input state :username)}))
      (=< nil 8)
      (div
       {}
       (input
        {:placeholder "Password",
         :value (:password state),
         :style ui/input,
         :on-input (on-input state :password)})))
     (=< nil 8)
     (div
      {:style {:text-align :right}}
      (span
       {:inner-text "Sign up",
        :style (merge style/link),
        :on-click (on-submit (:username state) (:password state) true)})
      (=< 8 nil)
      (span
       {:inner-text "Log in",
        :style (merge style/link),
        :on-click (on-submit (:username state) (:password state) false)}))))))
