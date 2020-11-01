(ns kafka-workbench.consumer
  (:require [kafka-workbench.conf :refer [consumer-config]]
            [jackdaw.client :as jc]
            [jackdaw.client.log :as jl]
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

(defn sub-poll [consumer]
  (fn []
    (log/info "Started Consumer Thread")
    (try
      (while true
        (doseq [msg (jc/poll consumer 100)]
          (log/infof "RX Msg: %s" msg)))
      (catch WakeupException _ (log/info "Consumer received WakeupException")))))


(defn subscriber
  "Create a consumer which polls a topic for messages."
  [topic group]
  (let [consumer (sub topic group)
        thr (Thread. (sub-poll consumer))
        sub-ops {:start (fn [] (.start thr))
                 :stop  (fn [] (.wakeup consumer))
                 :consumer (fn [] consumer)}]
    (fn [operation & args] (-> (sub-ops operation) (apply args)))))

(comment

  (def consumer (sub "foo"))

  (consumer :start)
  (consumer :stop)

  (bean (consumer :consumer))


  (.subscription consumer)

  (bean (.metrics consumer))

  (jc/poll consumer 1000)

  ;; show offset
  (-> :consumer consumer jc/position-all)

  (class consumer)

  consumer
  ;;
  )
