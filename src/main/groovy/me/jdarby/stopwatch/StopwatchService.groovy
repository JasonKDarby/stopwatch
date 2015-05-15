package me.jdarby.stopwatch

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class StopwatchService {

    //TODO: persistence
    private List stopwatches = []

    def start() {
        def stopwatch = [id: UUID.randomUUID().toString(), startTime: LocalDateTime.now()]
        stopwatches << stopwatch
        stopwatch
    }

    //TODO: return value is in seconds for no good reason
    def stop(String id) {
        def stopwatch0 = stopwatches.find { it.id == id && it.endTime == null }
        LocalDateTime endTime = LocalDateTime.now()
        [
                id: UUID.randomUUID().toString(),
                startTime: stopwatch0.startTime,
                endTime: endTime,
                duration: ChronoUnit.MILLIS.between(stopwatch0.startTime, endTime)
        ]
    }

    //TODO: this should either just return null (or equivalent (option?)) or throw an error
    def get(String id) {
        def stopwatch = stopwatches.find { it.id == id }
        stopwatch
    }
}
