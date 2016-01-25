(ns clojure-ttt.ai
  (:require [clojure-ttt.ui :refer :all]
            [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer :all]))

(defn zip-spaces-and-boards [spaces boards]
 (map #(zipmap [:space :board] %) (map vector spaces boards)))

(defn find-empty-spaces [board]
  (filter number? board))

(defn get-initial-marker [markers]
    (first markers))

(defn create-possible-boards [board spaces markers]
  (map #(mark-spot (first markers) % board) spaces))

(defn max-by-score [scored-boards]
  (apply max-key :score scored-boards))

(defn min-by-score [scored-boards]
  (apply min-key :score scored-boards))

(defn ai-config [board markers]
  (let [empty-spaces (find-empty-spaces board)
        possible-boards (create-possible-boards board empty-spaces markers)]
    (zip-spaces-and-boards empty-spaces possible-boards)))

(defn score-board [unscored-board-map markers initial-marker]
    (let [board-vector (:board unscored-board-map)
          current-marker (first markers)]

       (cond
        (and (win-game? board-vector) (= current-marker initial-marker)) (conj unscored-board-map {:score 10})
        (and (win-game? board-vector) (not (= current-marker initial-marker))) (conj unscored-board-map {:score -10})
        (tie-game? board-vector) (conj unscored-board-map {:score 0})
        :else (let [markers (reverse markers)
                    next-node-unscored-boards-map (ai-config board-vector markers)
                    scored-boards-map (map #(score-board % markers initial-marker) next-node-unscored-boards-map)]
                (if (= current-marker initial-marker)
                   (max-by-score scored-boards-map)
                   (min-by-score scored-boards-map))))))

(defn score-boards [unscored-boards markers initial-marker]
  (map #(score-board % markers initial-marker) unscored-boards))

(defn ai-make-move [board markers]
  (let [unscored-boards (ai-config board markers)
        initial-marker (get-initial-marker markers)
        scored-boards-coll (score-boards unscored-boards markers initial-marker)]
    (:space (max-by-score scored-boards-coll))))

