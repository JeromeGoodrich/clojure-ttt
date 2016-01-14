(ns clojure-ttt.core-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.core :refer :all]))

(describe "board-area"
  (it "creates a board with a specified height and width"
    (should= (vec (range 9)) (board-area 3 3))))


