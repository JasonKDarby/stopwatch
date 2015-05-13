package me.jdarby.stopwatch

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class StopwatchService {

    //TODO: persistence
    private List stopwatches = []

    String start() {
        String id = UUID.randomUUID()
        def stopwatch = [id: id, startTime: LocalDateTime.now()]
        stopwatches << stopwatch
        id
    }

    //TODO: return value is in seconds for no good reason
    long stop(String id) {
        def stopwatch = stopwatches.find { it.id == id && it.endTime == null }
        assert stopwatch != null
        stopwatch.endTime = LocalDateTime.now()
        ChronoUnit.SECONDS.between(stopwatch.startTime, stopwatch.endTime)
    }

    //TODO: this should either just return null (or equivalent (option?)) or throw an error
    Map get(String id) {
        def stopwatch = stopwatches.find { it.id == id }
        assert stopwatch != null
        stopwatch
    }

    //TODO: this should notify the caller whether it deletes something or not
    void delete(String id) {
        stopwatches.removeAll { it.id == id }
    }
}
