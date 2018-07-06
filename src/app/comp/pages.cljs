
(ns app.comp.pages
  (:require [hsl.core :refer [hsl]]
            [app.schema :as schema]
            [respo-ui.core :as ui]
            [respo.macros :refer [defcomp list-> cursor-> button <> span div a]]
            [respo.comp.space :refer [=<]]
            [clojure.string :as string]
            [respo-ui.comp.icon :refer [comp-icon]]
            [respo-alerts.comp.alerts :refer [comp-prompt comp-confirm]]
            [respo.comp.inspect :refer [comp-inspect]]))

(defcomp
 comp-pages
 (states router-data)
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
              (cursor->
               (str "prompt-" (:id page))
               comp-prompt
               states
               {:trigger (comp-icon :compose),
                :text "Add a new title:",
                :initial (:title page)}
               (fn [result d! m!]
                 (when (not (string/blank? result))
                   (d! :page/update-title {:id (:id page), :title result}))))
              (=< 8 nil)
              (cursor->
               :confirm
               comp-confirm
               states
               {:trigger (comp-icon :ios-trash), :text "Are you sure to delete?"}
               (fn [e d! m!] (d! :page/remove-one (:id page))))))]))))
  (cursor->
   :create-prompt
   comp-prompt
   states
   {:trigger (button {:style ui/button} (<> "Add")), :text "A title:"}
   (fn [result d! m!] (when (not (string/blank? result)) (d! :page/create result))))))
