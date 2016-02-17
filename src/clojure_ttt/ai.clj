(ns clojure-ttt.ai
  (:require [clojure-ttt.board :refer :all]
            [clojure.tools.cli :refer :all]))

(defn find-unmarked-spaces [board]
  (filter number? board))

(defn create-possible-boards [board-state markers space]
  (let [spaces (find-unmarked-spaces board-state)
        possible-board-states (map #(mark-spot (first markers) %1 board-state) spaces)]
    (map #(assoc {} :state %1 :space space) possible-board-states)))

(defn create-initial-boards [board-state markers]
  (let [spaces (find-unmarked-spaces board-state)
        possible-board-states (map #(mark-spot (first markers) %1 board-state) spaces)]
    (map #(assoc {} :state %1 :space %2) possible-board-states spaces)))

(defn max-by-score [scored-boards]
  (apply max-key :score scored-boards))

(defn min-by-score [scored-boards]
  (apply min-key :score scored-boards))

(defrecord Node [parent children board alpha beta depth player-type markers score])

(defn score-board [board-state player-type depth]
  (cond
    (and (win-game? board-state) (= "max" player-type)) (+ 10 depth)
    (and (win-game? board-state) (= "min" player-type)) (- depth 10)
    (or (= 0 depth) (tie-game? board-state)) 0
    :else nil))

(def minimax (memoize (fn [node]
  (let [current-node-score (score-board (:state (:board node)) (:player-type node) (:depth node))]
      (if (or current-node-score
              (empty? (:children node))
              (= 0 (:depth node))
              (<= (:beta node) (:alpha node)))
        (let [score (or current-node-score (:score node))]
          (if (nil? (:parent node))
            (:space (:board node))
            (if (= (:player-type node) "max")
              (if (or (nil? (:score (:parent node))) (> score (:score (:parent node))))
                (recur (-> (:parent node)
                           (assoc-in [:score] score)
                           (assoc-in [:alpha] (max score (:alpha (:parent node))))
                           (assoc-in [:board :space] (:space (:board node)))))
                (recur (:parent node)))
              (if (or (nil? (:score (:parent node)))
                      (< score (:score (:parent node))))
                (recur (-> (:parent node)
                           (assoc-in [:score] score)
                           (assoc-in [:beta] (min score (:beta (:parent node))))
                           (assoc-in [:board :space] (:space (:board node)))))
                (recur (:parent node))))))
        (let [new-node (Node. (update-in node [:children] rest)
                               (create-possible-boards (:state (first (:children node))) (reverse (:markers node)) (:space (first (:children node))))
                               (first (:children node))
                               (:alpha node)
                               (:beta node)
                               (dec (:depth node))
                               (if (= (:player-type node) "max") "min" "max")
                               (reverse (:markers node))
                               nil)]
          (recur new-node)))))))

(defn ai-make-move [board markers depth]
  (if (= (count board) (count (find-unmarked-spaces board)))
   8
   (let [node (Node. nil (create-initial-boards board markers) {:state board} -1000 1000 depth "min" markers nil)]
    (minimax node))))




