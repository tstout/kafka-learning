(ns kafka-workbench.producer
  (:require [kafka-workbench.conf :refer [producer-config]]
            [jackdaw.client :as jc]))

(defn publish-str [key val]
  {:pre [(string? key) (string? val)]}
  (with-open [my-producer (jc/producer producer-config)]
    @(jc/produce! my-producer {:topic-name "foo"} key val)))

(comment
  (publish-str "6" "Some string value 6")
  
  ;;
  )