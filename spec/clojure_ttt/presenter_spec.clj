(ns clojure-ttt.presenter-spec
  (require [speclj.core :refer :all]
           [clojure-ttt.presenter :refer :all]
           [clojure-ttt.config :refer :all]
           [clojure-ttt.spec-helper :refer :all]))

(describe "Console"
  (it "prompt outputs user's input"
    (let [io (new-test-console ["She walks, she talks, she's full of chalk..."])]
      (should= "She walks, she talks, she's full of chalk..." (prompt io "How's the cow?")))))
