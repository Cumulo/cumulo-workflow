
(set-env!
  :source-paths #{"src"}
  :dependencies '[[org.clojure/clojure       "1.8.0"       :scope "provided"]
                  [org.clojure/clojurescript "1.9.542"     :scope "provided"]
                  [andare                    "0.5.0"       :scope "provided"]
                  [adzerk/boot-cljs          "1.7.228-1"   :scope "provided"]
                  [cumulo/recollect          "0.1.7"]])

(require '[adzerk.boot-cljs   :refer [cljs]])

(def +version+ "0.1.0")

(task-options!
  pom {:project     'cumulo/workflow
       :version     +version+
       :description "Cumulo workflow server"
       :url         "https://github.com/Cumulo/cumulo-workflow"
       :scm         {:url "https://github.com/Cumulo/cumulo-workflow"}
       :license     {"MIT" "http://opensource.org/licenses/mit-license.php"}})

(deftask build-simple []
  (comp
    (cljs :optimizations :simple
          :compiler-options {:target :nodejs
                             :language-in :ecmascript5
                             :parallel-build true})
    (target)))
