
(ns app.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo-ui.colors :as colors]
            [respo.macros :refer [defcomp <> div span button]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo.comp.space :refer [=<]]
            [app.comp.header :refer [comp-header]]
            [app.comp.profile :refer [comp-profile]]
            [app.comp.login :refer [comp-login]]
            [respo-message.comp.msg-list :refer [comp-msg-list]]
            [app.comp.reel :refer [comp-reel]]))

(def style-alert {:font-family "Josefin Sans", :font-weight 100, :font-size 40})

(def chunk-offline
  (div
   {:style (merge ui/global ui/fullscreen ui/center)}
   (span
    {:style {:cursor :pointer}, :on-click (fn [e d! m!] (d! :effect/connect nil))}
    (<> "No connection!" style-alert))))

(def style-debugger {:bottom 0, :left 0, :max-width "100%"})

(defcomp
 comp-container
 (states store)
 (let [state (:data states), session (:session store)]
   (if (nil? store)
     chunk-offline
     (div
      {:style (merge ui/global ui/fullscreen ui/column)}
      (comp-header (:logged-in? store))
      (if (:logged-in? store)
        (let [router (:router store)]
          (case (:name router)
            :profile (comp-profile (:user store))
            (div
             {:style {:padding 16}}
             (button
              {:inner-text "Inc", :style ui/button, :on-click (fn [e d! m!] (d! :inc nil))})
             (=< 8 nil)
             (<> span (:count store) nil)
             (=< 8 nil)
             (<> span (pr-str router) nil))))
        (comp-login states))
      (comp-inspect "Store" store style-debugger)
      (comp-msg-list (get-in store [:session :notifications]) :session/remove-notification)
      (comp-reel (:reel-length store) {})))))

(def style-body {:padding "8px 16px"})
