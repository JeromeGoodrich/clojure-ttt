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

(defrecord Node [parent children board alpha beta depth node-type markers score])

(defn score-board [board-state node-type depth]
  (cond
    (and (win-game? board-state) (= "max" node-type)) (+ 10 depth)
    (and (win-game? board-state) (= "min" node-type)) (- depth 10)
    (or (= 0 depth) (tie-game? board-state)) 0
    :else nil))

(def minimax (memoize (fn [node]
  (let [current-node-score (score-board (:state (:board node)) (:node-type node) (:depth node))]
      (if (or current-node-score
              (empty? (:children node))
              (= 0 (:depth node))
              (<= (:beta node) (:alpha node)))
        (let [score (or current-node-score (:score node))]
          (if (nil? (:parent node))
            (:space (:board node))
            (if (= (:node-type node) "max")
              (if (or (nil? (:score (:parent node)))
                      (> score (:score (:parent node))))
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
                               (if (= (:node-type node) "max") "min" "max")
                               (reverse (:markers node))
                               nil)]
          (recur new-node)))))))

(defn ai-make-move [board markers depth]
  (if (= (count board) (count (find-unmarked-spaces board)))
   8
   (let [node (Node. nil (create-initial-boards board markers) {:state board} -1000 1000 depth "min" markers nil)]
    (minimax node))))
