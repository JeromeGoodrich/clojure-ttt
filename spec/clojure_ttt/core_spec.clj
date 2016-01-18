(ns clojure-ttt.core-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.core :refer :all]))

(describe "create-players"
  (it "creates players for the game"
    (should= [{:name "Jerome" :marker "X"} {:name "Griffin" :marker "O"}]
     (create-players 2))))
