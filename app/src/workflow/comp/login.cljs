
(ns workflow.comp.login
  (:require [respo.alias :refer [create-comp div input button]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.debug :refer [comp-debug]]
            [respo-ui.style :as ui]))

(defn on-input [mutate! k] (fn [e dispatch!] (mutate! k (:value e))))

(defn update-state [state k v] (assoc state k v))

(def style-title {:font-size 24, :font-weight 300, :font-family "Josefin Sans"})

(defn init-state [& args] {:password "", :username "", :signup? false})

(defn on-toggle [mutate! signup?] (fn [e dispatch!] (mutate! :signup? (not signup?))))

(defn on-submit [username password signup?]
  (fn [e dispatch!] (dispatch! (if signup? :user/sign-up :user/log-in) [username password])))

(defn render []
  (fn [state mutate!]
    (div
     {:style (merge ui/flex ui/column)}
     (div {:style style-title} (comp-text (if (:signup? state) "Sign up" "Log in") nil))
     (div
      {:style {}}
      (div
       {}
       (input
        {:style ui/input,
         :event {:input (on-input mutate! :username)},
         :attrs {:placeholder "Username", :value (:username state)}}))
      (comp-space nil 8)
      (div
       {}
       (input
        {:style ui/input,
         :event {:input (on-input mutate! :password)},
         :attrs {:placeholder "Password", :value (:password state)}})))
     (comp-space nil 8)
     (div
      {:style ui/flex}
      (button
       {:style (merge ui/button {:outline :none, :border :none}),
        :event {:click (on-submit (:username state) (:password state) (:signup? state))}}
       (comp-text "Submit" nil)))
     (comment comp-debug state nil)
     (comp-space nil 8)
     (if (:signup? state)
       (div
        {}
        (comp-text "Want to log in?" nil)
        (comp-space 8 nil)
        (button
         {:style ui/button, :event {:click (on-toggle mutate! (:signup? state))}}
         (comp-text "Log in" nil)))
       (div
        {}
        (comp-text "No account yet?" nil)
        (comp-space 8 nil)
        (button
         {:style ui/button, :event {:click (on-toggle mutate! (:signup? state))}}
         (comp-text "Sign up" nil)))))))

(def comp-login (create-comp :login init-state update-state render))
