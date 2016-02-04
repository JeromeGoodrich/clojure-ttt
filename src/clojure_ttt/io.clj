(ns clojure-ttt.io)

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

(defn display-board [board io board-display]
  (print-io io (str "\n"(board-display board))))

