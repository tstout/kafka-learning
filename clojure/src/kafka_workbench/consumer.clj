(ns kafka-workbench.consumer
  (:require [kafka-workbench.conf :refer [consumer-config]]
            [jackdaw.client :as jc]
            [jackdaw.client.log :as jl]
            [taoensso.timbre :as log])
  (:import [org.apache.kafka.common.errors WakeupException]))


(def topic-foo
  {:topic-name "foo"})

(defn sub [topic]
  {:pre [(string? topic)]}
  (->
   consumer-config
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


(defn subscriber [topic & opts]
  (let [consumer (sub topic)
        thr (Thread. (sub-poll consumer))
        sub-ops {:start (fn [] (.start thr))
                 :stop  (fn [] (.wakeup consumer))}]
    (fn [operation & args] (-> (sub-ops operation) (apply args)))))

(comment

  (def consumer (subscriber "foo"))

  (consumer :start)




  (.subscription consumer)

  (bean (.metrics consumer))

  (jc/poll consumer 1000)

  ;; show offset
  (jc/position-all consumer)

  (class consumer)

  consumer
  ;;
  )
