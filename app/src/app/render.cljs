
(ns app.render
  (:require [respo.render.html :refer [make-string]]
            [shell-page.core :refer [make-page spit slurp]]
            [app.comp.container :refer [comp-container]]))

(def base-info
  {:title "Cumulo", :icon "http://logo.cumulo.org/cumulo.png", :ssr nil, :inner-html nil})

(defn dev-page []
  (make-page
   ""
   (merge
    base-info
    {:styles ["http://localhost:8100/main.css"],
     :scripts ["/main.js" "/browser/lib.js" "/browser/main.js"]})))

(def preview? (= "preview" js/process.env.prod))

(defn prod-page []
  (let [html-content (make-string (comp-container {} nil))
        manifest (.parse js/JSON (slurp "dist/assets-manifest.json"))
        cljs-manifest (.parse js/JSON (slurp "dist/manifest.json"))
        cdn (if preview? "" "http://repo-cdn.b0.upaiyun.com/cumulo-workflow/")
        prefix-cdn (fn [x] (str cdn x))]
    (make-page
     html-content
     (merge
      base-info
      {:styles ["http://repo-cdn.b0.upaiyun.com/favored-fonts/main.css"
                (prefix-cdn (aget manifest "main.css"))],
       :scripts (map
                 prefix-cdn
                 [(aget manifest "main.js")
                  (-> cljs-manifest (aget 0) (aget "js-name"))
                  (-> cljs-manifest (aget 1) (aget "js-name"))])}))))

(defn main! []
  (if (= js/process.env.env "dev")
    (spit "target/index.html" (dev-page))
    (spit "dist/index.html" (prod-page))))
