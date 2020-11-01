(ns kafka-workbench.consumer
  (:require [kafka-workbench.conf :refer [consumer-config]]
            [jackdaw.client :as jc]
            [taoensso.timbre :as log])
  (:import [org.apache.kafka.common.errors WakeupException]))


(def topic-foo
  {:topic-name "foo"})

(defn sub [topic group]
  {:pre [(string? topic)]}
  (->
   (consumer-config {"group.id" group})
   jc/consumer
   (jc/subscribe [{:topic-name topic}])))

(defn sub-poll 
  "Creates a function which continuously polls a kafka topic.
   Intended to be used as a Runnable for a Thread instance."
  [consumer]
  (fn []
    (log/info "Started Consumer Thread")
    (try
      (while true
        (doseq [msg (jc/poll consumer 100)]
          (log/infof "RX Msg: %s" msg)))
      (catch WakeupException _ (log/info "Consumer received WakeupException")))))


(defn subscriber
  "Create a consumer which polls a topic for messages.
   The functions returned accpets three operations:
   :start - start a polling thread
   :stop - terminates the polling thread
   :consumer - returns a KafkaConsumer object"
  [topic group]
  (let [consumer (sub topic group)
        thr (Thread. (sub-poll consumer))
        sub-ops {:start (fn [] (.start thr))
                 :stop  (fn [] (.wakeup consumer))
                 :consumer (fn [] consumer)}]
    (fn [operation & args] (-> (sub-ops operation) (apply args)))))

(comment

  (def consumer (subscriber "foo" "foo.bar"))
  
  (consumer :start)
  (consumer :stop)
  (consumer :consumer)

  (.subscription consumer)

  (bean (.metrics consumer))

  (jc/poll consumer 1000)

  ;; show offset
  (-> :consumer consumer jc/position-all)

  consumer
  ;;
  )
