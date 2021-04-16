(ns kafka-workbench.producer
  (:require [kafka-workbench.conf :refer [producer-config]]
            [jackdaw.client :as jc]))

(defn publish-str [key val]
  {:pre [(string? key) (string? val)]}
  (with-open [my-producer (jc/producer producer-config)]
    @(jc/produce! my-producer {:topic-name "foo"} key val)))

(defn publish-n 
  "Publish n messages to the topic foo"
  [cnt]
  (with-open [my-producer (jc/producer producer-config)]
    (dotimes [n cnt]
      (jc/produce! my-producer {:topic-name "foo"} (str n) (str "some value " n)))))


(comment
  (publish-n 100)

  (time (publish-n 1))

  
  (publish-str "9" "Some string value 124")

  ;;
  )