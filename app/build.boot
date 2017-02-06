
(set-env!
  :resource-paths #{"polyfill" "src"}
  :dependencies '[[org.clojure/clojure       "1.8.0"       :scope "test"]
                  [org.clojure/clojurescript "1.9.293"     :scope "test"]
                  [andare                    "0.4.0"       :scope "test"]
                  [adzerk/boot-cljs          "1.7.228-1"   :scope "test"]
                  [adzerk/boot-reload        "0.4.13"      :scope "test"]
                  [cirru/boot-stack-server   "0.1.28"      :scope "test"]
                  [adzerk/boot-test          "1.1.2"       :scope "test"]
                  [mvc-works/hsl             "0.1.2"]
                  [respo                     "0.3.37"]
                  [respo/ui                  "0.1.6"]
                  [respo/message             "0.1.3"]
                  [cumulo/recollect          "0.1.5"]])

(require '[adzerk.boot-cljs   :refer [cljs]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-test   :refer :all])

(def +version+ "0.1.0")

(task-options!
  pom {:project     'cumulo/workflow
       :version     +version+
       :description "Cumulo workflow"
       :url         "https://github.com/Cumulo/cumulo-workflow"
       :scm         {:url "https://github.com/Cumulo/cumulo-workflow"}
       :license     {"MIT" "http://opensource.org/licenses/mit-license.php"}})

(deftask dev []
  (set-env!
    :asset-paths #{"assets/"})
  (comp
    (watch)
    (reload :on-jsload 'workflow.main/on-jsload!
            :cljs-asset-path ".")
    (cljs :compiler-options {:language-in :ecmascript5})
    (target :no-clean true)))

(deftask build-advanced []
  (set-env!
    :asset-paths #{"assets/"})
  (comp
    (cljs :optimizations :advanced
          :compiler-options {:language-in :ecmascript5
                             :pseudo-names true
                             :static-fns true
                             :parallel-build true
                             :optimize-constants true
                             :source-map true})
    (target :no-clean true)))

(deftask build []
  (comp
    (pom)
    (jar)
    (install)
    (target)))

(deftask deploy []
  (set-env!
    :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"}]))
  (comp
    (build)
    (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))

(deftask watch-test []
  (set-env!
    :source-paths #{"src" "test"})
  (comp
    (watch)
    (test :namespaces '#{workflow.test})))
