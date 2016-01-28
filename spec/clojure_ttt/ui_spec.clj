(ns clojure-ttt.ui-spec
  (require [speclj.core :refer :all]
           [clojure-ttt.ui :refer :all]))

(defn make-input [coll]
  (apply str (interleave coll (repeat "\n"))))

(describe "clojure-ttt.ui"
  (around [it]
    (with-out-str (it)))

  (it "tests the input of prompt"
   (should= "2"
     (with-in-str "2"
       (prompt "How many players will be playing?"))))

  (it "tests the output of prompt"
    (should= "Please enter your name\n"
      (with-out-str (with-in-str "Jerome Goodrich"
        (prompt "Please enter your name")))))

  (it "prints the board"
   (should= (str  "X 1 2\n"
                  "3 O 5\n"
                  "6 7 8\n"
                 "----------------\n")
      (with-out-str (print-board ["X" 1 2 3 "O" 5 6 7 8]))))

  (it "ignores invalid inputs from user and selects correct ones"
    (should= 3
      (with-in-str (make-input '("hello" 3))
        (human-make-move [0 1 2 3 4 5 6 7 8] [{:marker "X" :type "human"}
                                              {:marker "Y" :type "computer"}])))))
