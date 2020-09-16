(ns kafka-workbench.consumer
  (:require [kafka-workbench.conf :refer [consumer-config]]
            [jackdaw.client :as jc]
            [jackdaw.client.log :as jl]))


(def topic-foo
  {:topic-name "foo"})

(defn pull-evt [topic]
  (jc/consumer)
  )


(defn subscribe []
  (with-open [my-consumer (-> (jc/consumer consumer-config)
                              (jc/subscribe [topic-foo]))]
    (doseq [{:keys [key value partition timestamp offset]} (jl/log my-consumer 100)]
      (println "key: " key)
      (println "value: " value)
      (println "partition: " partition)
      (println "timestamp: " timestamp)
      (println "offset: " offset))))


(comment
  (subscribe)
  (jc/consumer )
  (+ 2 3)
  ;;
  )
