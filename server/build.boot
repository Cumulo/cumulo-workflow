
(set-env!
 :dependencies '[[org.clojure/clojurescript "1.9.216"     :scope "test"]
                 [org.clojure/clojure       "1.8.0"       :scope "test"]
                 [adzerk/boot-cljs          "1.7.228-1"   :scope "test"]
                 [figwheel-sidecar          "0.5.4-7"     :scope "test"]
                 [com.cemerick/piggieback   "0.2.1"       :scope "test"]
                 [org.clojure/tools.nrepl   "0.2.12"      :scope "test"]
                 [ajchemist/boot-figwheel   "0.5.4-6"     :scope "test"]
                 [cirru/stack-server        "0.1.7"       :scope "test"]
                 [binaryage/devtools        "0.5.2"       :scope "test"]
                 [adzerk/boot-test          "1.1.1"       :scope "test"]
                 [mvc-works/hsl             "0.1.2"       :scope "test"]
                 [cumulo/server             "0.1.0"]
                 [cumulo/shallow-diff       "0.1.1"]])

(require '[adzerk.boot-cljs   :refer [cljs]]
         '[stack-server.core  :refer [start-stack-editor! transform-stack]]
         '[adzerk.boot-test   :refer :all]
         '[boot-figwheel])

(def +version+ "0.1.0")

(task-options!
  pom {:project     'tiye/tiye-server
       :version     +version+
       :description "Tiye server"
       :url         "https://github.com/tiye/tiye.me"
       :scm         {:url "https://github.com/tiye/tiye.me"}
       :license     {"MIT" "http://opensource.org/licenses/mit-license.php"}})

(refer 'boot-figwheel :rename '{cljs-repl fw-cljs-repl}) ; avoid some symbols

(def all-builds
  [{:id "dev"
    :source-paths ["src/"]
    :compiler {:output-to "app.js"
               :output-dir "server_out/"
               :main 'workflow-server.main
               :target :nodejs
               :optimizations :none
               :source-map true}
    :figwheel {:build-id  "dev"
               :on-jsload 'workflow-server.main/on-jsload
               :autoload true
               :debug false}}])

(def figwheel-options
  {:repl true
   :http-server-root "target"
   :reload-clj-files false})

(deftask generate-code []
  (comp
    (transform-stack :filename "stack-sepal.ir")
    (target :dir #{"src/"})))

(deftask dev! []
  (set-env!
    :source-paths #{"src"})
  (comp
    (start-stack-editor! :port 7011)
    (target :dir #{"src/"})
    (repl)
    (figwheel
      :build-ids ["dev"]
      :all-builds all-builds
      :figwheel-options figwheel-options
      :target-path "target")
    (target)))

(deftask build-simple []
  (comp
    (transform-stack :filename "stack-sepal.ir")
    (cljs :optimizations :simple :compiler-options {:target :nodejs})
    (target)))

; use build-simple instead due to WebSocket reasons
(deftask build-advanced []
  (comp
    (transform-stack :filename "stack-sepal.ir" :port 7011)
    (cljs :optimizations :advanced :compiler-options {:target :nodejs})
    (target)))

(deftask rsync []
  (with-pre-wrap fileset
    (sh "rsync" "-r" "target" "tiye:servers/tiye" "--exclude" "main.out" "--delete")
    fileset))

(deftask watch-test []
  (set-env!
    :source-paths #{"src" "test"})
  (comp
    (watch)
    (test :namespaces '#{tiye-server.test})))
