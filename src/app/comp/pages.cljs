
(ns app.comp.pages
  (:require [hsl.core :refer [hsl]]
            [app.schema :as schema]
            [respo-ui.core :as ui]
            [respo-ui.colors :as colors]
            [respo.macros :refer [defcomp list-> cursor-> button <> span div a]]
            [respo.comp.space :refer [=<]]
            [clojure.string :as string]
            [respo-ui.comp.icon :refer [comp-icon]]
            [respo-alerts.comp.alerts :refer [comp-confirm comp-prompt]]
            [respo.comp.inspect :refer [comp-inspect]]))

(defcomp
 comp-pages
 (states router-data)
 (let [state (or (:data states)
                 {:delete-title {:show? false, :data nil},
                  :update-title {:show? false, :data nil},
                  :create-title {:show? false, :data nil}})]
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
                    (m! (assoc state :update-title {:show? true, :data page})))}
                 (comp-icon :compose))
                (=< 8 nil)
                (span
                 {:style {:cursor :pointer},
                  :on-click (fn [e d! m!]
                    (m! (assoc state :delete-title {:show? true, :data (:id page)})))}
                 (comp-icon :ios-trash))))]))))
    (button
     {:style ui/button,
      :on-click (fn [e d! m!] (m! (assoc state :create-title {:show? true, :data nil})))}
     (<> "Add"))
    (let [info (:delete-title state)]
      (when (:show? info)
        (comp-confirm
         "Are you sure to delete?"
         (fn [result d! m!]
           (when result (d! :page/remove-one (:data info)))
           (m! (assoc state :delete-title {:show? false, :data nil}))))))
    (let [info (:update-title state), page (:data info)]
      (when (:show? info)
        (cursor->
         :prompt
         comp-prompt
         states
         "Add a new title:"
         (:title page)
         (fn [result d! m!]
           (when (not (string/blank? result))
             (d! :page/update-title {:id (:id page), :title result}))
           (m! %cursor (assoc state :update-title {:show? false, :data nil}))))))
    (let [info (:create-title state)]
      (when (:show? info)
        (cursor->
         :prompt
         comp-prompt
         states
         "A title:"
         ""
         (fn [result d! m!]
           (when (not (string/blank? result)) (d! :page/create result))
           (m! %cursor (assoc state :create-title {:show? false, :data nil})))))))))
