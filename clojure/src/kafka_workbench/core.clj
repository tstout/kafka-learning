(ns kafka-workbench.core
  (:require [clojure.tools.cli :refer [parse-opts]])
(:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-p" "--produce num-msg" "Produce N messages"
    :default 1
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-t" "--topic topic-name" "Specify topic name"
    :default "foo"
    :validate [string? "Topic name must be a string"]]
   ["-c" "--consume" "Consume from a topic"]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn -main [& args]
  (parse-opts args))



(comment
  (-main '(:a :b) )
  ;;
  )