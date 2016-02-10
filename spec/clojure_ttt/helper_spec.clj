(ns clojure-ttt.helper-spec
  (:require [clojure-ttt.presenter :refer :all]))

(deftype TestConsole [input]
  Presenter
  (print-io [this something])
  (display-end-result [this result markers])
  (get-input [this]
    input)
  (display-board [this board])
  (prompt [this something]
    (get-input this)))

(defn new-test-console [input]
  (TestConsole. input))

(defn mock-exit [status message]
  status)
