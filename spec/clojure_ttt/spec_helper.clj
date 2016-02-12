(ns clojure-ttt.spec-helper
  (:require [clojure-ttt.presenter :refer :all]))

(deftype TestConsole [input]
  Presenter
  (print-io [this something])
  (display-end-result [this result markers])
  (get-input [this]
   (let [return-this (first @input)]
     (do (swap! input rest)
         return-this)))
  (display-board [this board])
  (prompt [this something]
    (get-input this)))

(defn new-test-console [input]
  (TestConsole. (atom input)))

(def mock-fn-calls (atom [ ]))

(deftype MockConsole [ ]
  Presenter
  (print-io [this something])
  (display-end-result [this result markers]
    (swap! mock-fn-calls conj "display-end-result"))
  (get-input [this])
  (display-board [this board]
    (swap! mock-fn-calls conj "display-board"))
  (prompt [this something]
    (get-input this)))

(defn new-mock-console [ ]
  (MockConsole.))

(defn mock-exit [status message]
  status)
