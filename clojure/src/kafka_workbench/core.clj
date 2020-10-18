(ns kafka-workbench.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [kafka-workbench.producer :refer [publish-n]]
            [kafka-workbench.consumer :refer [sub]])
  (:gen-class))

(def cli-options
  [["-p" "--produce num-msg" "Produce N messages"
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-t" "--topic topic-name" "Specify topic name"
    :default "foo"
    :validate [string? "Topic name must be a string"]]
   ["-c" "--consume" "Consume from a topic"]
   ["-h" "--help"]])

(defn run-cmd [options]
  (let [{:keys [produce consume topic]} options]
    (cond
      produce (publish-n produce)
      consume (println "consume"))))

(defn -main [& args]
  (let [{:keys [options 
                arguments 
                summary 
                errors]} (parse-opts args cli-options)]
    (cond 
      errors (println errors)
      (:help options) (println summary)
      :else (run-cmd options))))



(comment
  (-main "-p" "1")
  
  (-main "-c")
   
  ;;
  )