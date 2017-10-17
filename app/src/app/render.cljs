
(ns app.render
  (:require [respo.render.html :refer [make-string]]
            [shell-page.core :refer [make-page spit slurp]]
            [app.comp.container :refer [comp-container]]))

(def base-info
  {:title "Cumulo", :icon "http://cdn.tiye.me/logo/cumulo.png", :ssr nil, :inner-html nil})

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
        webpack-info (.parse js/JSON (slurp "dist/webpack-manifest.json"))
        cljs-info (.parse js/JSON (slurp "dist/cljs-manifest.json"))
        cdn (if preview? "" "http://cdn.tiye.me/cumulo-workflow/")
        prefix-cdn (fn [x] (str cdn x))]
    (make-page
     html-content
     (merge
      base-info
      {:styles ["http://cdn.tiye.me/favored-fonts/main.css"
                (prefix-cdn (aget webpack-info "main.css"))],
       :scripts (map
                 prefix-cdn
                 [(-> cljs-info (aget 0) (aget "js-name"))
                  (-> cljs-info (aget 1) (aget "js-name"))])}))))

(defn main! []
  (if (= js/process.env.env "dev")
    (spit "target/index.html" (dev-page))
    (spit "dist/index.html" (prod-page))))
