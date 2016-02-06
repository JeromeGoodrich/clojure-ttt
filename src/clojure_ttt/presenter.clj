(ns clojure-ttt.presenter)

(defprotocol Presenter
  (display-end-result [this result markers])
  (get-input [this])
  (display-board [this board])
  (prompt [this something]))

(deftype Console [display-options]
  Presenter
  (get-input [this]
    (read-line))
  (prompt [this something]
    (println something)
    (read-line))
  (display-end-result [this result markers]
     (if (= "win" result)
      (do (println "Game over! " (first markers) " wins!\n Play again? (y/n)")
          (read-line))
      (do (println "Game over! It's a tie!\n Play again? (y/n)")
          (read-line))))
  (display-board [this board]
    (println (str "\n"(display-options board)))))

(defn new-console [display-options]
  (Console. display-options))


