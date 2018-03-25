
(ns app.comp.profile
  (:require [hsl.core :refer [hsl]]
            [app.schema :as schema]
            [respo-ui.core :as ui]
            [respo-ui.colors :as colors]
            [respo.macros :refer [defcomp <> span div a]]
            [respo.comp.space :refer [=<]]))

(defcomp
 comp-profile
 (user)
 (div
  {:style (merge ui/flex {:padding 16})}
  (<> (str "Hello! " (:name user)))
  (=< 8 nil)
  (a
   {:style {:font-size 14,
            :cursor :pointer,
            :background-color colors/motif-light,
            :color :white,
            :padding "0 8px"},
    :on-click (fn [e dispatch! mutate!]
      (dispatch! :user/log-out nil)
      (.removeItem js/localStorage (:storage-key schema/configs)))}
   (<> span "Log out" nil))))
