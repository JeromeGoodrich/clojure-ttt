(ns clojure-ttt.core
  (:require [clojure-ttt.ui :refer :all]
           [clojure-ttt.board :refer :all]))

(defn create-players [number]
  (cond
    (= "1" number) (let [players [{:name (prompt "What is your name?") :marker (prompt "Select a marker (it can be any letter)")}
                                  {:name "TicTacJoe" :marker (prompt "Select a marker for the computer")}]
                       order (prompt "Who will go first? (me/comp)")]
                    (cond
                      (= order "me") players
                      (= order "comp") (reverse players)))

    (= "2" number) (let [players [{:name (prompt "Player 1, what is your name?")
                                   :marker (prompt "Select a marker (it can be any letter)")}
                                  {:name (prompt "Player 2, what is your name?")
                                   :marker (prompt "Select a marker (it can be any letter not chosen by player 1)")}]] players)))

(defn make-move [player]
  (cond
    (= (:name (first player)) "TicTacJoe") true
    :else (prompt "Select a space using the numbers of the spaces above")))

(defn end-game [board players]
  (cond
    (clojure-ttt.board/tie-game? board) (print "Game over! It's a tie!")
    :else (print (str "Game over! " (:name (first players)) " wins!"))))

(defn game-loop [board players]
    (loop [players players
           board board]
      (if (clojure-ttt.board/game-over? board)
        (end-game board players)
        (recur (clojure-ttt.board/mark-spot (:marker (first players))
                                            (make-move (first players))
                                            board)
               (reverse players)))))


