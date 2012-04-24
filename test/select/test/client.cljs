(ns select.test.client
  (:require
    [menodora.core :as mc])
  (:use
    [select :only (fill sizzle-selector get-selector-fn default-css-selector)]
    [menodora.predicates :only (eq)])
  (:use-macros
    [menodora :only (defsuite describe should expect)]))

(defn make-root
  [s]
  (let [r (. js/document (createElement "div"))]
    (set! (. r -innerHTML) s)
    r))

(defn dfill
  [root-node query-map]
  (fill root-node query-map :selector-fn default-css-selector))

(defsuite core-tests
  (describe "fill"
    (should "replace singles"
      (let [root (make-root "The <verb>quick</verb> brown fox")]
        (expect eq "The SLOW brown fox"
          (do
            (dfill root {"verb" "SLOW"})
            (. root -innerHTML)))))
    (should "modify singles"
      (let [root (make-root "The <verb>quick</verb> brown fox")]
        (expect eq "The SLOW brown fox"
          (do
            (dfill root {"verb" #(set! (. % -outerText) "SLOW")})
            (. root -innerHTML)))))
    (should "replace multiples"
      (let [root (make-root "A <adj>little</adj> <adj>squirmy</adj> worm")]
        (expect eq "A ADJECTIVE ADJECTIVE worm"
          (do
            (dfill root {"adj" "ADJECTIVE"})
            (. root -innerHTML)))))
    (should "modify multiples"
      (let [root (make-root "A <adj>little</adj> <adj>squirmy</adj> worm")]
        (expect eq "A ADJECTIVE ADJECTIVE worm"
          (do
            (dfill root {"adj" #(set! (. % -outerText) "ADJECTIVE")})
            (. root -innerHTML)))))
    (should "ignore non-matching queries"
      (let [root (make-root "foo")]
        (expect eq "foo"
          (do
            (dfill root {".nothing" "NEVER SEEN"})
            (. root -innerHTML))))))

  (describe "sizzle-selector"
    (should "be first preference by default"
      (expect eq sizzle-selector (get-selector-fn nil)))
    (should "work"
      (let [root (make-root "A <adj>little</adj> <adj>squirmy</adj> worm")]
        (expect eq "A ADJECTIVE ADJECTIVE worm"
          (do
            (fill root {"adj" "ADJECTIVE"})
            (. root -innerHTML)))))))

;;. vim: set lispwords+=defsuite,describe,should,expect:
