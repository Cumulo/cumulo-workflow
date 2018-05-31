
(ns app.comp.profile
  (:require [hsl.core :refer [hsl]]
            [app.schema :as schema]
            [respo-ui.core :as ui]
            [respo-ui.colors :as colors]
            [respo.macros :refer [defcomp list-> <> span div a]]
            [respo.comp.space :refer [=<]]
            [app.config :as config]))

(defcomp
 comp-profile
 (user members)
 (div
  {:style (merge ui/flex {:padding 16})}
  (div
   {:style {:font-family ui/font-fancy, :font-size 32, :font-weight 100}}
   (<> (str "Hello! " (:name user))))
  (=< nil 16)
  (div
   {:style ui/row}
   (<> "Members:")
   (=< 8 nil)
   (list->
    {:style ui/row}
    (->> members
         (map
          (fn [[k username]]
            [k
             (div
              {:style {:padding "0 8px",
                       :border (str "1px solid " (hsl 0 0 80)),
                       :border-radius "16px",
                       :margin "0 4px"}}
              (<> username))])))))
  (=< nil 48)
  (div
   {}
   (a
    {:style {:font-size 14,
             :cursor :pointer,
             :background-color colors/motif-light,
             :color :white,
             :padding "0 8px"},
     :on-click (fn [e dispatch! mutate!]
       (dispatch! :user/log-out nil)
       (.removeItem js/localStorage (:storage-key config/site)))}
    (<> span "Log out" nil)))))
