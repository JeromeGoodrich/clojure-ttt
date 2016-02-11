(ns clojure-ttt.spec-helper
  (:require [clojure-ttt.presenter :refer :all]))

(deftype TestConsole [input]
  Presenter
  (print-io [this something])
  (display-end-result [this result markers])
  (get-input [this]
   (let [return-this (first @input)]
     (swap! input rest)
         return-this))
  (display-board [this board])
  (prompt [this something]
    (get-input this)))

(defn new-test-console [input]
  (TestConsole. (atom input)))

(defn mock-exit [status message]
  status)
