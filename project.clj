(defproject mike/ring-compojure-friend-selmer "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.taoensso/timbre "4.8.0"]
                 [com.stuartsierra/component "0.3.2"]
                 [ring/ring-core "1.6.0-RC2"]
                 [ring/ring-anti-forgery "1.0.1"]
                 [ring/ring-defaults "0.2.3"]
                 [ring/ring-jetty-adapter "1.6.0-RC2"]
                 [ring-json-response "0.2.0"]
                 [compojure "1.5.2"]
                 [com.cemerick/friend "0.3.0-SNAPSHOT"]
                 [selmer "1.10.7"]]
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :profiles {:uberjar {:aot :all
                       :main .main}
             :dev {:source-paths ["dev"]
                   :target-path "target/dev"
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [clj-http "3.4.1"]
                                  [org.clojure/data.json "0.2.6"]]}}
  :repl-options {:init-ns user})
