
(set-env!
 :dependencies '[[org.clojure/clojurescript "1.9.89"      :scope "test"]
                 [org.clojure/clojure       "1.8.0"       :scope "test"]
                 [adzerk/boot-cljs          "1.7.228-1"   :scope "test"]
                 [figwheel-sidecar          "0.5.4-7"     :scope "test"]
                 [com.cemerick/piggieback   "0.2.1"       :scope "test"]
                 [org.clojure/tools.nrepl   "0.2.12"      :scope "test"]
                 [ajchemist/boot-figwheel   "0.5.4-6"     :scope "test"]
                 [cirru/boot-cirru-sepal    "0.1.9"       :scope "test"]
                 [binaryage/devtools        "0.5.2"       :scope "test"]
                 [adzerk/boot-test          "1.1.1"       :scope "test"]
                 [mvc-works/hsl             "0.1.2"       :scope "test"]
                 [cumulo/server             "0.1.0"]
                 [cumulo/shallow-diff       "0.1.1"]])

(require '[adzerk.boot-cljs   :refer [cljs]]
         '[cirru-sepal.core   :refer [transform-cirru]]
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
    :source-paths ["compiled/app"]
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

(deftask compile-cirru []
  (set-env!
    :source-paths #{"cirru/"})
  (comp
    (transform-cirru)
    (target :dir #{"compiled/"})))

(deftask watch-compile []
  (set-env!
    :source-paths #{"cirru/"})
  (comp
    (watch)
    (transform-cirru)
    (target :dir #{"compiled/"})))

(deftask dev []
  (set-env!
    :source-paths #{"compiled/app"})
  (comp
    (repl)
    (figwheel
      :build-ids ["dev"]
      :all-builds all-builds
      :figwheel-options figwheel-options
      :target-path "target")
    (target)))

(deftask build-simple []
  (set-env!
    :source-paths #{"cirru/app"})
  (comp
    (transform-cirru)
    (cljs :optimizations :simple :compiler-options {:target :nodejs})
    (target)))

(deftask build-advanced []
  (set-env!
    :source-paths #{"cirru/app"})
  (comp
    (transform-cirru)
    (cljs :optimizations :advanced :compiler-options {:target :nodejs})
    (target)))

(deftask rsync []
  (with-pre-wrap fileset
    (sh "rsync" "-r" "target" "tiye:servers/tiye" "--exclude" "main.out" "--delete")
    fileset))

(deftask watch-test []
  (set-env!
    :source-paths #{"cirru/src" "cirru/test"})
  (comp
    (watch)
    (transform-cirru)
    (test :namespaces '#{tiye-server.test})))
