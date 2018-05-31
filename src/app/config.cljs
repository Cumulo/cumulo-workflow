
(ns app.config )

(def dev? (do ^boolean js/goog.DEBUG))

(def site
  {:storage-key "workflow-storage",
   :port 5021,
   :dev-ui "http://localhost:8100/main.css",
   :release-ui "http://cdn.tiye.me/favored-fonts/main.css",
   :cdn "http://cdn.tiye.me/cumulo-workflow/"})
