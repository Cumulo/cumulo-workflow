
(ns app.page
  (:require [respo.render.html :refer [make-string]]
            [shell-page.core :refer [make-page spit slurp]]
            [app.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [app.schema :as schema]
            [app.config :as config]))

(def base-info
  {:title (:title config/site),
   :icon (:icon config/site),
   :ssr nil,
   :inline-styles [(slurp "entry/main.css")]})

(defn dev-page []
  (make-page "" (merge base-info {:styles [(:dev-ui config/site)], :scripts ["/client.js"]})))

(def preview? (= "preview" js/process.env.prod))

(defn prod-page []
  (let [html-content (make-string (comp-container {} nil))
        assets (read-string (slurp "dist/assets.edn"))
        cdn (if preview? "" (:cdn-url config/site))
        prefix-cdn #(str cdn %)]
    (make-page
     html-content
     (merge
      base-info
      {:styles [(:release-ui config/site)],
       :scripts (map #(-> % :output-name prefix-cdn) assets)}))))

(defn main! []
  (if (= js/process.env.env "dev")
    (spit "target/index.html" (dev-page))
    (spit "dist/index.html" (prod-page))))
