;; # Select: simple templating

(defproject
  select "0.1.0-SNAPSHOT"
  :description "Selector based templating for Clojurescript"

  :dependencies
  [[jasminejs "0.1.0-SNAPSHOT"]]

  :dev-dependencies
  [[menodora "0.1.4-SNAPSHOT"]]

  :plugins
  [[lein-cst "0.2.3"]]

  :cst
  {:suites [select.test/core-tests]}

  :story
  {:output "doc/index.html"})

;;%include README.md
;;%include src/select.cljs
