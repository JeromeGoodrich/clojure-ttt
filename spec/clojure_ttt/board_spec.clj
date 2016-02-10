(ns clojure-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.board :refer :all]))

(describe "create-board"
  (it "creates a board of a specified size"
    (should= (vec (range 9)) (create-board 3))
    (should= (vec (range 16)) (create-board 4))))

(describe "marking the board"
 (context "3x3 board"
  (it "mark the board with a given marker on a specified spot"
    (should= ["X" 1 2 3 4 5 6 7 8] (mark-spot "X" 0 [0 1 2 3 4 5 6 7 8]))
    (should= ["X" 1 2 "O" 4 5 6 7 8] (mark-spot "O" 3 ["X" 1 2 3 4 5 6 7 8]))))

  (context "4x4 board"
    (it "mark the board with a given marker on a specified spot"
      (should= [0 1 2 3 4 5 6 "X" 8 9 10 11 12 13 14 15]
              (mark-spot "X" 7 [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]))
      (should= [0 1 2 "O" 4 5 6 "X" 8 9 10 11 12 13 14 15]
              (mark-spot "O" 3 [0 1 2 3 4 5 6 "X" 8 9 10 11 12 13 14 15])))))

(describe "winning horizontally"
  (context "3x3 board"
    (it "declares a winner with the correct number of adjacent horizontal markers"
      (should= true (horizontal-winner? ["X" "X" "X" "O" 4 "O" 6 7 8]))
      (should= nil (horizontal-winner? ["X" 1 "X" "O" 4 "O" 6 7 8]))))

  (context "4x4 board"
    (it "declares a winner with the correct number of adjacent horizontal markers"
      (should= true (horizontal-winner? ["X" "X" "X" "X" 4 "O" 6 7 8 9 "O" 11 "O" 13 14 15]))
      (should= nil (horizontal-winner? ["X" 1 "X" "O" 4 "O" 6 7 8 9 10 11 12 13 14 15])))))

(describe "winning vertically"
  (context "3x3 board"
    (it "declares a winner the correct number of adjacent vertical markers"
      (should= true (vertical-winner? ["X" 1 2 "X" 4 5 "X" 7 8]))
      (should= nil (vertical-winner? ["X" 1 2 "O" 4 5 "X" 7 8]))))

  (context "4x4 board"
    (it "declares a winner the correct number of adjacent vertical markers"
      (should= true (vertical-winner? ["X" "O" 2 3 "X" 5 "O" 7 "X" 9 10 11 "X" 13 14 "O"]))
      (should= nil (vertical-winner? ["X" 1 2 "O" 4 5 "X" 7 8 9 10 11 12 13 14 15])))))

(describe "winning diagonally"
  (context "3x3 board"
    (it "declares a winner diagonally"
      (should= true (diagonal-winner? ["X" 1 2 3 "X" 5 6 7 "X"]))
      (should= nil (diagonal-winner? ["O" 1 2 "X" 4 5 "X" 7 8]))))

  (context "4x4 board"
    (it "declares a winner diagonally"
      (should= true (diagonal-winner? ["X" 1 2 3 4 "X" 6 7 8 9 "X" 11 12 13 14 "X"]))
      (should= nil (diagonal-winner? ["O" 1 2 "X" 4 5 "X" 7 8 9 10 11 12 13 14 15])))))

(describe "tie game"
  (context "3x3 board"
    (it "declares a tie game"
      (should= true (tie-game? ["X" "O" "X" "O" "O" "X" "X" "X" "O"]))
      (should= false (tie-game? ["X" 1 2 "X" 4 5 "X" 7 8]))))

  (context "4x4 board"
    (it "declares a tie game"
      (should= true (tie-game? ["X" "O" "X" "O" "O" "X" "X" "X" "O" "X" "O" "X" "O" "X" "O" "X"]))
      (should= false (tie-game? ["X" 1 2 "X" 4 5 "X" 7 8 9 10 11 12 13 14 15])))))

(describe "game over"
  (context "3x3 board"
    (it "declares the end of the game"
      (should= true (game-over? ["X" "O" "X" "O" "O" "X" "X" "X" "O"]))
      (should= true (game-over? ["X" "X" "X" 3 4 5 6 7 8]))
      (should= true (game-over? ["X" 1 2 "X" 4 5 "X" 7 8]))
      (should= true (game-over? ["X" 1 2 3 "X" 5 6 7 "X"]))
      (should= true (game-over? [0 1 "X" 3 "X" 5 "X" 7 8]))
      (should= nil (game-over? [0 1 2 3 4 5 6 7 8]))))

  (context "4x4 board"
    (it "declares the end of the game"
      (should= true (game-over? ["X" "X" "X" "X" 4 "O" 6 7 8 9 "O" 11 "O" 13 14 15]))
      (should= true (game-over? ["X" "O" 2 3 "X" 5 "O" 7 "X" 9 10 11 "X" 13 14 "O"]))
      (should= true (game-over? ["X" 1 2 3 4 "X" 6 7 8 9 "X" 11 12 13 14 "X"]))
      (should= true (game-over? ["X" "O" "X" "O" "O" "X" "X" "X" "O" "X" "O" "X" "O" "X" "O" "X"]))
      (should= nil (game-over? [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])))))
