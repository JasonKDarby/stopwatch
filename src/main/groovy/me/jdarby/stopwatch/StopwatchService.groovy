package me.jdarby.stopwatch

import java.time.Instant
import java.time.temporal.ChronoUnit

class StopwatchService {

    //TODO: persistence
    private List stopwatches = []

    def start() {
        def stopwatch = [id: UUID.randomUUID().toString(), startTime: Instant.now()]
        stopwatches << stopwatch
        stopwatch
    }

    //TODO: return value is in milliseconds for no good reason
    def stop(String id) {
        def stopwatch0 = stopwatches.find { it.id == id && it.endTime == null }
        if(stopwatch0 == null) return null
        Instant endTime = Instant.now()
        def stopwatch1 = [
                id: UUID.randomUUID().toString(),
                startTime: stopwatch0.startTime,
                endTime: endTime,
                duration: ChronoUnit.MILLIS.between(stopwatch0.startTime, endTime),
                parentId: stopwatch0.id
        ]
        stopwatches << stopwatch1
        stopwatch1
    }

    //TODO: this should either just return null (or equivalent (option?)) or throw an error
    def findStopwatch(String id) {
        stopwatches.find { it.id == id }
    }

    def findChildren(String parentId) {
        if(parentId == null) return null
        stopwatches.findAll { it.parentId == parentId }
    }


}
