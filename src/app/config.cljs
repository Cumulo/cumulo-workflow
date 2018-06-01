
(ns app.config )

(def dev? (do ^boolean js/goog.DEBUG))

(def site
  {:storage-key "workflow-storage",
   :port 5021,
   :title "Cumulo",
   :icon "http://cdn.tiye.me/logo/cumulo.png",
   :dev-ui "http://localhost:8100/main.css",
   :release-ui "http://cdn.tiye.me/favored-fonts/main.css",
   :cdn-path "cumulo-workflow",
   :upload-path "Cumulo/workflow",
   :server-path "workflow"})
