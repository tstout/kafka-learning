(ns kafka-workbench.consumer
  (:require [kafka-workbench.conf :refer [consumer-config]]
            [jackdaw.client :as jc]
            [jackdaw.client.log :as jl]))


(def topic-foo
  {:topic-name "foo"})

(defn sub [topic]
  {:pre [(string? topic)]}
  (->
   consumer-config
   jc/consumer
   (jc/subscribe [{:topic-name topic}])))


(defn subscribe []
  (with-open [my-consumer (-> (jc/consumer consumer-config)
                              (jc/subscribe [topic-foo]))]
    (doseq [{:keys [key value partition timestamp offset]} (jl/log my-consumer 100)]
      (println "key: " key)
      (println "value: " value)
      (println "partition: " partition) 
      (println "timestamp: " timestamp)
      (println "offset: " offset))))

;;(defn consumer)


(defn subscriber [topic & opts]
  ;; TODO - implement thread closure
  ;; (let [thr (Thread.)
  ;;       ])
  (prn topic)
  (prn opts))



(comment
  (def consumer (sub "foo"))

  
  (.subscription consumer)

  (bean (.metrics consumer))

  (jc/poll consumer 1000)
  
  ;; show offset
  (jc/position-all consumer)

  (count (jc/poll consumer 1000))

  (class consumer)
  
  consumer
  ;;
  )
