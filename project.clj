;; # Select: simple templating

(defproject
  select "0.1.0"
  :description "Selector based templating for Clojurescript"

  :dependencies
  [[cst "0.2.4"]]

  :dev-dependencies
  [[menodora "0.1.4"]]

  :plugins
  [[lein-cst "0.2.4"]]

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
