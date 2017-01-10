
(set-env!
 :dependencies '[[org.clojure/clojure       "1.8.0"       :scope "test"]
                 [org.clojure/clojurescript "1.9.293"     :scope "test"]
                 [andare                    "0.4.0"       :scope "test"]
                 [adzerk/boot-cljs          "1.7.228-1"   :scope "test"]
                 [cirru/boot-stack-server   "0.1.24"      :scope "test"]
                 [adzerk/boot-test          "1.1.1"       :scope "test"]
                 [mvc-works/hsl             "0.1.2"       :scope "test"]
                 [cumulo/recollect          "0.1.4"]])

(require '[adzerk.boot-cljs   :refer [cljs]]
         '[stack-server.core  :refer [start-stack-editor! transform-stack]]
         '[adzerk.boot-test   :refer :all])

(def +version+ "0.1.0")

(task-options!
  pom {:project     'tiye/tiye-server
       :version     +version+
       :description "Cumulo workflow server"
       :url         "https://github.com/tiye/tiye.me"
       :scm         {:url "https://github.com/tiye/tiye.me"}
       :license     {"MIT" "http://opensource.org/licenses/mit-license.php"}})

(deftask generate-code []
  (comp
    (transform-stack :filename "stack-sepal.ir")
    (target :dir #{"src/"})))

(deftask editor! []
  (comp
    (wait)
    (start-stack-editor! :port 7011)
    (target :dir #{"src/"})))

(deftask build-simple []
  (comp
    (transform-stack :filename "stack-sepal.ir")
    (cljs :optimizations :simple
          :compiler-options {:target :nodejs
                             :language-in :ecmascript5
                             :parallel-build true})
    (target)))

; use build-simple instead due to WebSocket reasons
(deftask build-advanced []
  (comp
    (transform-stack :filename "stack-sepal.ir")
    (cljs :optimizations :advanced
          :compiler-options {:target :nodejs
                             :language-in :ecmascript5
                             :parallel-build true})
    (target)))

(deftask rsync []
  (with-pre-wrap fileset
    (sh "rsync" "-r" "target" "repo.tiye.me:servers/tiye" "--exclude" "main.out" "--delete")
    fileset))

(deftask watch-test []
  (set-env!
    :source-paths #{"src" "test"})
  (comp
    (watch)
    (test :namespaces '#{workflow-server.test})))
