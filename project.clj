(defproject algorithms-course-2013 "0.1.0-SNAPSHOT"

  :description "Pallet intilery deployment"

  :dependencies [[org.clojure/clojure "1.4.0"]
                 ;[org.apache.commons/commons-lang3 "3.1"]
                 ;[org.clojure/math.numeric-tower "0.0.2"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 
                 ]
  :profiles 
    {:dev
      {:dependencies [[com.cemerick/pomegranate "0.0.13"]]
       ;:plugins
    }}

  :local-repo-classpath true
  :repositories
    {"sonatype-snapshots" "https://oss.sonatype.org/content/repositories/snapshots"
     "sonatype" "https://oss.sonatype.org/content/repositories/releases/"}

  :repl-options {:init (-main)}

  :main repl
)
