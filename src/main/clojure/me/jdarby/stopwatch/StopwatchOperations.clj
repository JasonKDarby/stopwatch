(ns me.jdarby.stopwatch.StopwatchOperations
  (:use [clojure.core]))

(def records (agent []))

(defn start!
  "create a \"start\" record"
  [records]
  (let [record {:id (str (java.util.UUID/randomUUID)) :startTime (java.time.Instant/now)}]
    (send records conj record)))

(defn stop!
  "create a \"stop\" record"
  [records id]
  (let [startTime ((first (findRecord records id)) :startTime) endTime (java.time.Instant/now)]
    (send records conj
          {:id        (str (java.util.UUID/randomUUID))
           :startTime startTime
           :endTime   endTime
           :duration  5
           :parentId  id})))

(defn findRecord
  [records id]
  (filter #(= (% :id) id) @records))

(defn findChildren
  [records parentId]
  (filter #(= (% :parentId) parentId) @records))