(ns clojure-ttt.io-spec
  (require [speclj.core :refer :all]
           [clojure-ttt.io :refer :all]
           [clojure-ttt.config :refer :all]))

(deftype TestIO [input]
      IO
      (print-io [this something]
                something)
      (read-io [this]
                input))

(defn new-test-io [input]
  (TestIO. input))

(defn make-input [coll]
  (apply str (interleave coll (repeat "\n"))))


(describe "clojure-ttt.ui"
  (it "choose-move gets output from user"
    (let [io (new-test-io "5"))



