(ns clojure-ttt.config
  (:require [clojure-ttt.board :refer :all]))

(defn me-first-config [options]
  (let [player1 {:marker (:player1 options)
                 :type "human"}
        player2 {:marker (:player2 options)
                 :type "computer"}
        board (create-board (:board options))] [player1 player2 board]))

