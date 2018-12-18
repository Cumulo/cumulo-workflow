
(ns app.config )

(def cdn?
  (cond
    (exists? js/window) false
    (exists? js/process) (= "true" js/process.env.cdn)
    :else false))

(def dev?
  (let [debug? (do ^boolean js/goog.DEBUG)]
    (if debug?
      (cond
        (exists? js/window) true
        (exists? js/process) (not= "true" js/process.env.release)
        :else true)
      false)))

(def site
  {:port 5021,
   :title "Cumulo",
   :icon "http://cdn.tiye.me/logo/cumulo.png",
   :dev-ui "http://localhost:8100/main.css",
   :release-ui "http://cdn.tiye.me/favored-fonts/main.css",
   :cdn-url "http://cdn.tiye.me/cumulo-workflow/",
   :cdn-folder "tiye.me:cdn/cumulo-workflow",
   :upload-folder "tiye.me:repo/Cumulo/workflow/",
   :server-folder "tiye.me:servers/workflow",
   :theme "#eeeeff",
   :storage-key "workflow-storage",
   :storage-file "storage.edn"})
