
(ns app.comp.pages
  (:require [hsl.core :refer [hsl]]
            [app.schema :as schema]
            [respo-ui.core :as ui]
            [respo-ui.colors :as colors]
            [respo.macros :refer [defcomp list-> button <> span div a]]
            [respo.comp.space :refer [=<]]
            [clojure.string :as string]
            [respo-ui.comp.icon :refer [comp-icon]]))

(defcomp
 comp-pages
 (router-data)
 (div
  {:style {:padding 16}}
  (list->
   {:style (merge ui/row {:flex-wrap :wrap})}
   (->> router-data
        (map
         (fn [[k page]]
           [k
            (div
             {:style (merge
                      ui/row-parted
                      {:margin-right 16,
                       :margin-bottom 16,
                       :padding 8,
                       :min-width 240,
                       :border (str "1px solid " (hsl 0 0 86)),
                       :border-radius "4px"})}
             (<> (:title page) {:cursor :pointer})
             (div
              {:style ui/row}
              (span
               {:style {:cursor :pointer},
                :on-click (fn [e d! m!]
                  (let [new-title (js/prompt "A new title?" (:title page))]
                    (when (not (string/blank? new-title))
                      (d! :page/update-title {:id (:id page), :title new-title}))))}
               (comp-icon :compose))
              (=< 8 nil)
              (span
               {:style {:cursor :pointer},
                :on-click (fn [e d! m!]
                  (let [confirmation? (js/confirm "Sure to delete?")]
                    (when confirmation? (d! :page/remove-one (:id page)))))}
               (comp-icon :ios-trash))))]))))
  (button
   {:style ui/button,
    :on-click (fn [e d! m!]
      (let [title (js/prompt "Page title?")]
        (when (not (string/blank? title)) (d! :page/create title))))}
   (<> "Add"))))
