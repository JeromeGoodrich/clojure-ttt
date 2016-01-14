(ns clojure-ttt.core)

(defn board-area [height width]
  (vec (range (* height width))))
