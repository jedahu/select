;; # The code
(ns select
  (:use
    [goog.dom :only (replaceNode)])
  ;;<?
  ;; Testing imports
  (:require
    [clojure.string :as string])
  (:use-macros
    [jasminejs.core :only (describe it expect)])
  ;;?>
  )

;; Selector functions take a root node, a query, and return a sequence of
;; descendent nodes that match the query. This makes it easy to change the
;; selector inplementation to whatever system is desired (e.g. xpath).

;; Two selector functions are provided by Select. The first wraps the browser's
;; implementation of `querySelectorAll`. It needs a helper function to convert
;; the results of `querySelectorAll` to a sequence.

(defn seq<-
  [coll]
  (for [x (range (. coll -length))]
    (aget coll x)))

(defn default-css-selector
  [root-node query]
  (seq<- (. root-node (querySelectorAll query))))

;; The second uses [Sizzle](http://sizzlejs.com).
(defn sizzle-selector
  [root-node query]
    (js/Sizzle query root-node))

;; If no selector function is provided to [[fill]] and `window.Sizzle` exists,
;; then `sizzle-selector` is used, otherwise `default-css-selector` is used.
(defn get-selector-fn [selector-fn]
  (or selector-fn
      (cond
        (aget js/window "Sizzle") sizzle-selector
        (aget js/document "querySelectorAll") default-css-selector
        :else (throw
                (js/Error.
                  "No usable selector-fn found. Please supply one.")))))

;; The `fill` function takes a root node, a map from queries to functions, and
;; an optional `:selector-fn` key with a function value. `fill` simply iterates
;; through each query in the map and calls the associated function for each
;; matching node. The functions take a single node argument and may do one of
;; two things:
;;
;; 1. return a single node to replace the matched node;
;;
;; 2. return nil, and modify the matched node in place.
;;
;; Queries that don't match anything are ignored.
(defn ^:export fill
  [root-node query-map & opts]
  (let [opts (apply hash-map opts)
        query-fn (get-selector-fn (:selector-fn opts))]
    (doseq [[query func] query-map
            matched-node (query-fn root-node query)]
      (when-let [new-node (func matched-node)]
        (replaceNode new-node matched-node)))))

;;<?
(describe "fill"
  (it "should replace singles"
    (let [root (. js/document (createElement "div"))]
      (set! (. root -innerHTML)
            "The <span class='verb'>quick</span> brown fox")
      (expect = "The SLOW brown fox"
        (do
          (fill root {".verb" #(set! (. % -outerText) "SLOW")})
          (. root -innerHTML)))))
  (it "should replace multiples"
    (let [root (. js/document (createElement "div"))]
      (set! (. root -innerHTML)
            "A <span class='adj'>little</span> <span class='adj'>squirmy</span> worm")
      (expect = "A ADJECTIVE ADJECTIVE worm"
        (do
          (fill root {".adj" #(set! (. % -outerText) "ADJECTIVE")})
          (. root -innerHTML)))))
  (it "should ignore non-matching queries"
    (let [root (. js/document (createElement "div"))]
      (expect = ""
        (do
          (fill root {".nothing" #(set! (. % -innerText) "NEVER SEEN")})
          (. root -innerHTML))))))
;;?>

;;. vim: set lispwords+=describe,expect,it:
