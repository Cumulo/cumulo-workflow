
(ns workflow.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]
            [workflow.comp.header :refer [comp-header]]
            [workflow.comp.profile :refer [comp-profile]]
            [workflow.comp.login :refer [comp-login]]
            [respo-message.comp.msg-list :refer [comp-msg-list]]))

(def style-alert {:font-size 40, :font-weight 100, :font-family "Josefin Sans"})

(def style-body {:padding "8px 16px"})

(def style-debugger {:bottom 0, :max-width "100%", :left 0})

(defn render [store]
  (fn [state mutate!]
    (if (nil? store)
      (div
       {:style (merge ui/global ui/fullscreen ui/center)}
       (comp-text "No connection!" style-alert))
      (div
       {:style (merge ui/global ui/fullscreen ui/column)}
       (comp-header (:logged-in? store))
       (div
        {:style style-body}
        (div
         {:style (merge ui/row style-body)}
         (if (:logged-in? store)
           (let [router (:router store)]
             (case (:name router)
               :profile (comp-profile (:user store))
               (div {} (comp-text (str "404 page: " (pr-str router)) nil))))
           (comp-login))))
       (comp-debug store style-debugger)
       (comp-msg-list (get-in store [:session :notifications]) :session/remove-notification)))))

(def comp-container (create-comp :container render))
