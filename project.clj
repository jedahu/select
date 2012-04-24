;; # Select: simple templating

(defproject
  select "0.1.0-SNAPSHOT"
  :description "Selector based templating for Clojurescript"

  :dependencies
  [[cst "0.2.4-SNAPSHOT"]]

  :dev-dependencies
  [[menodora "0.1.4-SNAPSHOT"]]

  :plugins
  [[lein-cst "0.2.4-SNAPSHOT"]]

  :cst
  {:suites [select.test.client/core-tests]
   :runners
   {:console-browser {:cljs menodora.runner.console/run-suites-browser
                      :proc select.test.server/core-server}}
   :runner :console-browser}

  :story
  {:output "doc/index.html"})

;;%include README.md
;;%include src/select.cljs
