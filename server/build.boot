
(set-env!
  :source-paths #{"src"}
  :dependencies '[[org.clojure/clojure       "1.8.0"       :scope "provided"]
                  [org.clojure/clojurescript "1.9.473"     :scope "provided"]
                  [andare                    "0.5.0"       :scope "provided"]
                  [adzerk/boot-cljs          "1.7.228-1"   :scope "provided"]
                  [cirru/boot-stack-server   "0.1.30"      :scope "provided"]
                  [cumulo/shallow-diff       "0.1.3"       :scope "provided"]
                  [fipp                      "0.6.9"       :scope "provided"]
                  [cumulo/recollect          "0.1.7"]])

(require '[adzerk.boot-cljs   :refer [cljs]])

(def +version+ "0.1.0")

(task-options!
  pom {:project     'tiye/tiye-server
       :version     +version+
       :description "Cumulo workflow server"
       :url         "https://github.com/tiye/tiye.me"
       :scm         {:url "https://github.com/tiye/tiye.me"}
       :license     {"MIT" "http://opensource.org/licenses/mit-license.php"}})

(deftask build-simple []
  (comp
    (cljs :optimizations :simple
          :compiler-options {:target :nodejs
                             :language-in :ecmascript5
                             :parallel-build true})
    (target)))

; use build-simple instead due to WebSocket reasons
(deftask build-advanced []
  (comp
    (cljs :optimizations :advanced
          :compiler-options {:target :nodejs
                             :language-in :ecmascript5
                             :parallel-build true})
    (target)))
