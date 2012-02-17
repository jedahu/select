;; # Select: simple templating

(defproject
  select "0.1.0-SNAPSHOT"
  :description "Selector based templating for Clojurescript"

  ;;<?
  ;; The only dependencies are those needed for testing.
  :dependencies
  [[jasminejs "0.1.0-SNAPSHOT"]]

  :dev-dependencies
  [[lein-clojurescript "1.1.1-SNAPSHOT"]]
  ;;?>

  :cljs
  {:test-cmd ["phantomjs" "test.js"]
   :optimizations :whitespace
   :pretty-print true
   :output-to "out/all.js"
   :output-dir "out"})

;;%include README.md
;;%include src/select.cljs
