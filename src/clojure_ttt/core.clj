(ns clojure-ttt.core
  (:require [clojure-ttt.ui :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer :all]
            [clojure-ttt.ai :refer :all]))

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
(def cli-options
  [["-p1" "--player1 TYPE" "Player1 type"]
   ["-p2" "--player2 TYPE" "Player2 type"]
   ["-m1" "--marker1 MARKER" "Player1 marker"
    :default "X"]
   ["-m2" "--marker2 MARKER" "Player2 marker"
    :default "O"]
   ["-b"  "--board SIZE" "board size"
    :default 3
    :parse-fn #(Integer. %)]
   ["-h"  "--help"]])

(defn error-mesg [errors]
  (print "The following errors occurred while parsing your command:")
  (println errors))

(defn make-move [board players]
      (if (= (:name (first players)) "TicTacJoe")
        (ai-make-move board (map #(:marker %) players))
        (Integer. (prompt "Select a space using the numbers of the spaces above"))))

(defn end-game [board players]
  (cond
    (tie-game? board) (print "Game over! It's a tie!")
    :else (print (str "Game over! " (:name (second players)) " wins!"))))

(defn game-loop [board players]
    (loop [board board
           players players]
      (print-board board)
      (if (game-over? board)
        (end-game board players)
        (recur (mark-spot (:marker (first players))
                                    (make-move board players)
                                    board)
               (reverse players)))))

(defn -main []
  (let [board (clojure-ttt.board/create-board 3)
       players (create-players (prompt "Welcome to TicTacToe! How many humans will be playing?"))]
 (game-loop board players)))


