(ns kafka-workbench.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [kafka-workbench.producer :refer [publish-n]]
            [kafka-workbench.consumer :refer [subscriber]]
            [kafka-workbench.logging :refer [config-logging!]]
            [taoensso.timbre :as log])
  (:gen-class))

(def cli-options
  [["-p" "--produce num-msg" "Produce N messages"
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-t" "--topic topic-name" "Specify topic name"
    :default "foo"
    :validate [string? "Topic name must be a string"]]
   ["-g" "--group group-name" "Specify consumer group id"
    :default "com.github.tstout.my-consumer"
    :validate [string? "consumer group id must be a string"]]
   ["-c" "--consume" "Consume from a topic"]
   ["-h" "--help"]])

(defn run-cmd [options]
  (let [{:keys [group produce consume topic]} options]
    (cond
      produce (do
                (log/infof "publishing %d messages to %s" produce topic)
                (publish-n produce))
      consume (do
                (log/infof "consuming from topic %s with consumer id %s" topic group)
                ((subscriber topic group) :start)))))

(defn -main [& args]
  (config-logging! :dev)
  (let [{:keys [options
                arguments
                summary
                errors]} (parse-opts args cli-options)]
    (log/infof "Parsed opts: %s" (str options))
    (log/infof "Opts Values: %s" (str arguments))
    (cond
      errors (println errors)
      (:help options) (println summary)
      :else (run-cmd options))))



(comment
  (-main "-p" "1")

  (str {:a 1})

  (-main "-c")

  (-main "-h")

  ;;
  )