(ns kafka-workbench.conf
  (:require [jackdaw.admin :as ja]))

(def producer-config
  {"bootstrap.servers" "stout-pi3:9092"
   "key.serializer" "org.apache.kafka.common.serialization.StringSerializer"
   "value.serializer" "org.apache.kafka.common.serialization.StringSerializer"
   "acks" "all"
   "client.id" "sample-producer"})

;; see https://stackoverflow.com/a/32392174/59768
;; for a discussion of offsets
;; (defn kafka-consumer-config
;;   [group-id]
;;   {"bootstrap.servers" bootstrap-servers
;;    "group.id" group-id
;;    "auto.offset.reset" "earliest"
;;    "enable.auto.commit" "true"})

(def default-consumer-config
  {"bootstrap.servers" "stout-pi3:9092"
   "group.id"  "com.github.tstout.my-consumer"
   "key.deserializer" "org.apache.kafka.common.serialization.StringDeserializer"
   "value.deserializer" "org.apache.kafka.common.serialization.StringDeserializer"})

(defn consumer-config 
  ([opts] (merge default-consumer-config opts))
  ([] (consumer-config {})))

(defn mk-adm-client []
  (ja/->AdminClient {"bootstrap.servers" "stout-pi3:9092"}))


(comment
  (def client (mk-adm-client))
  
  (merge {:a 1} {:a 2})
  
  (consumer-config {"group.id" "foo-1"})
  
  (consumer-config)
  
  
  (ja/describe-cluster client)
  (ja/describe-topics client)
  (ja/describe-topics-configs client ["foo"])
  (ja/list-topics client)
  @(ja/list-topics* client)
  (ja/get-broker-config client 0)
  
  
  
  ;;
  )