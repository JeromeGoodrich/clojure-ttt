(ns clojure-ttt.ui)

(defprotocol IO
  (print-io [this anything])
  (read-io [this]))

(deftype ConsoleIO [ ]
  IO
  (print-io [this anything]
    (println anything))
  (read-io [this]
    (read-line)))

(defn new-console-io [ ]
  (ConsoleIO.))

(defn prompt [io something]
  (print-io io something)
  (read-io io))

(defn display-board [board board-type]
   (println (str (board-type board) "----------------\n")))

(defn invalid-move []
  (println "Invalid move, please choose a valid space to move to"))

(defn choose-space [markers io]
  (let [current-marker (first markers)]
    (prompt io (str "It is your turn, " current-marker ". Choose an unmarked space."))))

(defn human-make-move [board markers io]
  (try (Integer. (choose-space markers io))
    (catch Exception e (do
                         (print (str (.getMessage e) " That's not a number. Let's try that again!\n"))
                         (human-make-move board markers)))))



