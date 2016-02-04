(ns clojure-ttt.ui-spec
  (require [speclj.core :refer :all]
           [clojure-ttt.ui :refer :all]
           [clojure-ttt.config :refer :all]))

(deftype TestIO [input]
      IO
      (print-io [this something]
                something)
      (read-io [this]
                input))

(defn new-test-io [input]
  (TestIO. input))

(defn make-input [coll]
  (apply str (interleave coll (repeat "\n"))))


(describe "clojure-ttt.ui"
  (it "choose-move gets output from user"
    (let [io (new-test-io "5"))



  (it "ignores invalid inputs from user and selects correct ones"
    (should= 3
      (with-in-str (make-input '("hello" 3))
        (human-make-move [0 1 2 3 4 5 6 7 8] ["X" "O"] io pretty-board)))))
