
(ns app.config (:require [app.util :refer [get-env!]]))

(def bundle-builds #{"release" "local-bundle"})

(def dev?
  (if (exists? js/window)
    (do ^boolean js/goog.DEBUG)
    (not (contains? bundle-builds (get-env! "mode")))))

(def site
  {:storage-key "workflow-storage",
   :port 5021,
   :title "Cumulo",
   :icon "http://cdn.tiye.me/logo/cumulo.png",
   :dev-ui "http://localhost:8100/main.css",
   :release-ui "http://cdn.tiye.me/favored-fonts/main.css",
   :cdn-url "http://cdn.tiye.me/cumulo-workflow/",
   :cdn-folder "tiye.me:cdn/cumulo-workflow",
   :upload-folder "tiye.me:repo/Cumulo/workflow/",
   :server-folder "tiye.me:servers/workflow",
   :theme "#eeeeff"})
