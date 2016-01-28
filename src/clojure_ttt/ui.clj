(ns clojure-ttt.ui)

(defn prompt [message]
  (println message)
  (read-line))

(defn print-board [board]
  (let [size (int (Math/sqrt  (count board)))]
  (->> (partition size board)
       (map #(apply str %))
       (map #(clojure.string/join " " %))
       (map println)
       (dorun))
  (print (str "----------------\n"))))

(defn invalid-move []
  (println "Invalid move, please choose a valid space to move to"))

(def cli-options
  [["-f" "--first MARKER" "Player1 marker"
    :id :player1
    :default "X"
    :validate [#(= (count %) 1) "Marker is too long"]]
   ["-s" "--second MARKER" "Player2 marker"
    :id :player2
    :default "O"]
   ["-b"  "--board SIZE" "board size"
    :default 3
    :parse-fn #(Integer. %)]
   ["-h"  "--help"]])

(defn choose-space [players]
  (let [current-player (:marker (first players))]
  (prompt (str "It is your turn, " current-player ". Choose an unmarked space."))))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (clojure.string/join \newline errors)))

(defn usage [options-summary]
  (->>["Welcome to Clojure TicTacToe."
       ""
       "Usage: lein run action [options]"
       ""
       "Examples:"
       "  lein run me-first -f x -s o      comp v. human, human goes first"
       "  lein run head-to-head -f q -s H -b 4    human v. human on a 4x4 board"
       ""
       "Options Summary:"
       options-summary
       ""
       "Actions:"
       "  me-first       play first against computer"
       "  comp-first     play second against the computer"
       "  head-to-head   play against another human"]
    (clojure.string/join \newline)))

(defn winner [players]
  (print (str "Game over! " (:marker (second players)) " wins!\n")))

(defn tie []
  (print (str "Game over! It's a tie!\n")))
