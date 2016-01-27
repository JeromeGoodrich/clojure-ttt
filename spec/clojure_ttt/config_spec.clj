(ns clojure-ttt.config_spec
 (:require [speclj.core :refer :all]
           [clojure-ttt.config :refer :all]))

(describe "me-first-config"
  (it "creates the parts for a human v. computer game where the human moves first"
    (should= [{:marker "X" :type "human"}
              {:marker "O" :type "computer"}
              [0 1 2 3 4 5 6 7 8]]
            (game-config "me-first" {:player1 "X" :player2 "O" :board 3}))))

(describe "comp-first-config"
  (it "creates the parts for a human v. computer game where the computer moves first"
    (should= [{:marker "F" :type "computer"}
              {:marker "G" :type "human"}
              [0 1 2 3 4 5 6 7 8]]
            (game-config "comp-first" {:player1 "F" :player2 "G" :board 3}))))

(describe "head-to-head-config"
  (it "creates the parts for a human v. human game"
    (should= [{:marker "F" :type "human"}
              {:marker "G" :type "human"}
              [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]]
            (game-config "head-to-head" {:player1 "F" :player2 "G" :board 4}))))

