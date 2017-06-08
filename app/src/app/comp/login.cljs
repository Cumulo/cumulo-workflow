
(ns app.comp.login
  (:require [respo.alias :refer [create-comp div input button]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.debug :refer [comp-debug]]
            [respo-ui.style :as ui]
            [app.schema :as schema]))

(defn on-toggle [cursor state]
  (fn [e dispatch!] (dispatch! :states [cursor (update state :signup? not)])))

(defn on-submit [username password signup?]
  (fn [e dispatch!]
    (dispatch! (if signup? :user/sign-up :user/log-in) [username password])
    (.setItem js/localStorage (:storage-key schema/configs) [username password])))

(defn on-input [cursor state k]
  (fn [e dispatch!] (dispatch! :states [cursor (assoc state k (:value 1))])))

(def style-title {:font-size 24, :font-weight 300, :font-family "Josefin Sans"})

(def initial-state {:signup? false, :username "", :password ""})

(def comp-login
  (create-comp
   :login
   (fn [states]
     (fn [cursor]
       (let [state (or (:data states) initial-state)]
         (div
          {:style (merge ui/flex ui/column)}
          (div
           {}
           (comp-text (if (:signup? state) "Sign up" "Log in") style-title)
           (if (:signup? state)
             (div
              {}
              (comp-text "Want to log in?" nil)
              (comp-space 8 nil)
              (div
               {:style ui/clickable-text, :event {:click (on-toggle cursor state)}}
               (comp-text "Log in" nil)))
             (div
              {}
              (comp-text "No account yet?" nil)
              (comp-space 8 nil)
              (div
               {:style ui/clickable-text, :event {:click (on-toggle cursor state)}}
               (comp-text "Sign up" nil)))))
          (div
           {:style {}}
           (div
            {}
            (input
             {:style ui/input,
              :attrs {:placeholder "Username", :value (:username state)},
              :event {:input (on-input cursor state :username)}}))
           (comp-space nil 8)
           (div
            {}
            (input
             {:style ui/input,
              :attrs {:placeholder "Password", :value (:password state)},
              :event {:input (on-input cursor state :password)}})))
          (comp-space nil 8)
          (div
           {:style ui/flex}
           (button
            {:style (merge ui/button {:outline :none, :border :none}),
             :event {:click (on-submit (:username state) (:password state) (:signup? state))}}
            (comp-text "Submit" nil)))
          (comment comp-debug state nil)))))))
