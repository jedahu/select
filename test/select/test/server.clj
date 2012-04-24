(ns select.test.server
  (:require
    [cst.server :as cst]))

(def core-server
  #(cst/serve-cljs % :head "<script src='/sizzle/sizzle.js'></script>"))
