(ns clojure-ttt.core
  (require [clojure-ttt.ui :refer :all]))

(defn create-players [number]
  (cond
    (= 2 number) (let [players [{:name (prompt "Player 1, what is your name?") :marker (prompt "Select a marker (it can be any letter)")}
                                {:name (prompt "Player 2, what is your name?") :marker (prompt "Select a marker (it can be any letter not chosen by player 1)")}]] players)))



