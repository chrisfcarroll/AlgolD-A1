(defproject algorithms-course-2013 "0.1.0-SNAPSHOT"

  :description "Pallet intilery deployment"

  :dependencies [[org.clojure/clojure "1.4.0"]
                 ;[org.apache.commons/commons-lang3 "3.1"]
                 ;[org.clojure/math.numeric-tower "0.0.2"]
                 ]

  :dev-dependencies [[org.cloudhoist/pallet "0.7.2" :type "test-jar"]]
                     
  :profiles {:dev
             {:dependencies
              [[org.cloudhoist/pallet "0.7.2" :classifier "tests"]]
              :plugins [[org.cloudhoist/pallet-lein "0.5.2"]]}}
  :local-repo-classpath true
  :repositories
    {"sonatype-snapshots" "https://oss.sonatype.org/content/repositories/snapshots"
     "sonatype" "https://oss.sonatype.org/content/repositories/releases/"}

  :repl-options {:init (do (require 'repl) (repl/force-slf4j))}

  :main commandline
)
