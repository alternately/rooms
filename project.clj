(defproject rooms "0.1.0-SNAPSHOT"
  :description "A little text based adventure platform. Not many features (yet :) )"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot rooms.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
