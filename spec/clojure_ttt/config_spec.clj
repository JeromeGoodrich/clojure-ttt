(ns clojure-ttt.config_spec
 (:require [speclj.core :refer :all]
           [clojure-ttt.config :refer :all]))

(describe "me-first-config"
  (it "creates the parts for a human v. computer game where the human moves first"
    (should= [{:marker "X" :type "human"}
              {:marker "O" :type "computer"}
              [0 1 2 3 4 5 6 7 8]]
            (me-first-config {:player1 "X" :player2 "O" :board 3}))))
