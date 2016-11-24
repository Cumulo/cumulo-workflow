
(ns workflow.comp.home
  (:require [respo.alias :refer [create-comp div a]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.space :refer [comp-space]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [workflow.comp.topics :refer [comp-topics]]
            [workflow.comp.login :refer [comp-login]]
            [workflow.comp.room :refer [comp-room]]))

(defn on-log-out [e dispatch!] (dispatch! :user/log-out nil))

(def style-trigger
  {:color :white,
   :font-size 14,
   :background-color colors/motif-light,
   :cursor :pointer,
   :padding "0 8px"})

(def style-container {:padding "8px 16px"})

(defn render [store]
  (fn [state mutate!]
    (div
     {:style (merge ui/row style-container)}
     (comp-topics (:topics store) (:logged-in? store))
     (comp-space 8 nil)
     (if (:logged-in? store)
       (let [router (get-in store [:state :router])]
         (if (= (:name router) :topic)
           (comp-room (:seeing-messages store) (:data router))
           (div
            {:style ui/flex}
            (comp-text (str "Hello! " (get-in store [:user :name])) nil)
            (comp-space 8 nil)
            (a {:style style-trigger, :event {:click on-log-out}} (comp-text "Log out" nil)))))
       (comp-login)))))

(def comp-home (create-comp :home render))
