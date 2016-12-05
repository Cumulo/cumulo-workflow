
(ns workflow.comp.profile
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span a]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]
            [respo.comp.space :refer [comp-space]]))

(defn on-log-out [e dispatch!] (dispatch! :user/log-out nil))

(def style-trigger
  {:color :white,
   :font-size 14,
   :background-color colors/motif-light,
   :cursor :pointer,
   :padding "0 8px"})

(defn render [user]
  (fn [state mutate!]
    (div
     {:style ui/flex}
     (comp-text (str "Hello! " (:name user)) nil)
     (comp-space 8 nil)
     (a {:style style-trigger, :event {:click on-log-out}} (comp-text "Log out" nil)))))

(def comp-profile (create-comp :profile render))
