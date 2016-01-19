(ns clojure-ttt.core-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.core :refer :all]))

(describe "create-players"

  (it "creates 2 human players for the game"
    (pending "don't want to go through output each time")
    (should= [{:name "Jerome" :marker "X"} {:name "Griffin" :marker "O"}]
     (create-players "2")))

  (it "creates a human and computer player for the game"
    (pending "don't want to go through output each time")
    (should= [{:name "TicTacJoe" :marker "X"} {:name "Jerome" :marker "O"}]
      (create-players "1"))))

(describe "make-move"
  (it "selects a spot on the board to move to"
    (should= "5"
      (with-in-str "5"
        (make-move [{:name "Jerome" :marker "X"}])))))

(describe "end-game"
  (it "ends the game with a winner"
    (should= "Game over! Jerome wins!"
      (with-out-str (end-game ["X" "X" "X" 3 4 5 6 7 8]
                              [{:name "Jerome" :marker "X"}]))))

  (it "ends the game as a tie"
    (should= "Game over! It's a tie!"
      (with-out-str (end-game ["X" "O" "X" "O" "X" "X" "O" "X" "O"]
                              [{:name "TicTacJoe" :maker "O"}])))))

(describe "game-loop"
  (context "plays through the last move of a game"
    (it "ends in a tie"
      (pending "multiple outputs")
      (should= "Game over! It's a tie!"
        (with-in-str "7"
        (with-out-str (game-loop ["X" "O" "X" "O" "X" "X" "O" 7 "O"]
                                 [{:name "Jerome" :marker "X"} {:name "Sol" :marker "O"}])))))

    (it "ends in a win"
      (should= "Game over! Jerome wins!"
        (with-in-str "6"
         (with-out-str (game-loop ["X" "O" "X" "O" "X" 5 6 7 "O"]
                                  [{:name "Jerome" :marker "X"} {:name "Sol" :marker "O"}])))))))
