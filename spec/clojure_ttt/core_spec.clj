(ns clojure-ttt.core-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.core :refer :all]
            [clojure-ttt.ui :refer :all]))

(describe "make-move"
  (it "selects a spot on the board to move to"
    (should= 5
      (with-in-str "5"
        (make-move ["X" "O" "X" 3 "X" 5 "O" 7 "O"]
                   [{:type "human" :marker "X"}
                    {:type "computer" :marker "O"}]))))

  (it "computer move"
    (should= 5
      (make-move ["X" 2 "O" 3 "X" 5 "X" 7 "O"]
                   [{:type "computer" :marker "O"}
                    {:type "human" :marker "X"}]))))


(describe "game-loop"
  (context "plays through the last move of a game"
    (it "ends in a tie"
      (should= "Game over! It's a tie!"
        (with-in-str "7"
        (with-out-str (game-loop ["X" "O" "X" "O" "X" "X" "O" 7 "O"]
                                 [{:type "human" :marker "X"} {:type "human" :marker "O"}])))))

    (it "ends in a win"
      (should= "Game over! Jerome wins!"
        (with-in-str "6"
         (with-out-str (game-loop ["X" "O" "X" "O" "X" 5 6 7 "O"]
                                  [{:type "human" :marker "X"} {:type "human" :marker "O"}])))))))

