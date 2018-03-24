
(ns app.schema )

(def configs {:storage-key "workflow-storage", :port 5021})

(def dev? (do ^boolean js/goog.DEBUG))
