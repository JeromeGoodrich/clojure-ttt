(ns clojure-ttt.ui-spec
  (require [speclj.core :refer :all]
           [clojure-ttt.ui :refer :all]
           [clojure-ttt.config :refer :all]))

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
   (should= (str  "X  1  2\n"
                  "3  O  5\n"
                  "6  7  8\n"
                 "----------------\n")
      (with-out-str (display-board ["X" 1 2 3 "O" 5 6 7 8] ugly-board))))

  (it "prints the board"
   (should= (str  "X  1  2  3\n"
                  "O  5  6  7\n"
                  "8  9  10  11\n"
                 "12  13  14  15\n"
                 "----------------\n")
      (with-out-str (display-board ["X" 1 2 3 "O" 5 6 7 8 9 10 11 12 13 14 15] ugly-board ))))

  (it "prints a pretty board"
    (should= (str "___|_X_|___\n"
                  "_X_|_O_|_O_\n"
                  "   | X |   \n"
                  "----------------\n")
             (with-out-str (display-board [0 "X" 2 "X" "O" "O" 6 "X" 8] pretty-board))))

  (it "prints a pretty board"
    (should= (str "___|_X_|___|___\n"
                  "_X_|_O_|_O_|___\n"
                  "___|___|___|___\n"
                  "   | X |   |   \n"
                  "----------------\n")
             (with-out-str (display-board [0 "X" 2 3 "X" "O" "O" 7 8 9 10 11 12 "X" 14 15] pretty-board))))


  (it "ignores invalid inputs from user and selects correct ones"
    (should= 3
      (with-in-str (make-input '("hello" 3))
        (human-make-move [0 1 2 3 4 5 6 7 8] [{:marker "X" :type "human"}
                                              {:marker "Y" :type "computer"}])))))
