(ns clojure-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.board :refer :all]))

(describe "create-board"
  (it "creates a board of a specified sizw"
    (should= (vec (range 9)) (create-board 3))))

(describe "marking the board"
  (it "mark the board with a given marker on a specified spot"
    (should= ["X" 1 2 3 4 5 6 7 8] (mark-spot "X" "0" [0 1 2 3 4 5 6 7 8]))
    (should= ["X" 1 2 "O" 4 5 6 7 8] (mark-spot "O" "3" ["X" 1 2 3 4 5 6 7 8]))))

(describe "winning horizontally"
  (it "declares a winner with the correct number of adjacent horizontal markers"
    (should= true (horizontal-winner? ["X" "X" "X" "O" 4 "O" 6 7 8]))
    (should= nil (horizontal-winner? ["X" 1 "X" "O" 4 "O" 6 7 8]))))

(describe "winning vertically"
  (it "declares a winner the correct number of adjacent vertical markers"
    (should= true (vertical-winner? ["X" 1 2 "X" 4 5 "X" 7 8]))
    (should= nil (vertical-winner? ["X" 1 2 "O" 4 5 "X" 7 8]))))

(describe "winning diagonally"
  (it "declares a winner diagonally"
    (should= true (diagonal-winner? ["X" 1 2 3 "X" 5 6 7 "X"]))
    (should= nil (diagonal-winner? ["O" 1 2 "X" 4 5 "X" 7 8]))))

(describe "tie game"
  (it "declares a tie game"
    (should= true (tie-game? ["X" "O" "X" "O" "O" "X" "X" "X" "O"]))
    (should= false (tie-game? ["X" 1 2 "X" 4 5 "X" 7 8]))))

(describe "game over"
  (it "declares the end of the game"
    (should= true (game-over? ["X" "O" "X" "O" "O" "X" "X" "X" "O"]))
    (should= true (game-over? ["X" "X" "X" 3 4 5 6 7 8]))
    (should= true (game-over? ["X" 1 2 "X" 4 5 "X" 7 8]))
    (should= true (game-over? ["X" 1 2 3 "X" 5 6 7 "X"]))
    (should= true (game-over? [0 1 "X" 3 "X" 5 "X" 7 8]))
    (should= nil (game-over? [0 1 2 3 4 5 6 7 8]))))




