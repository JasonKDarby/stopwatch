package me.jdarby.stopwatch

import me.jdarby.stopwatch.persisters.Persister

import javax.inject.Inject
import java.time.Instant
import java.time.temporal.ChronoUnit

class StopwatchService<P extends Persister> {

    private P persister

    @Inject
    StopwatchService(P persister) {
        this.persister = persister
    }

    def start() {
        def stopwatch = [id: UUID.randomUUID().toString(), startTime: Instant.now()]
        persister << stopwatch
        stopwatch
    }

    //TODO: return value is in milliseconds for no good reason
    def stop(String id) {
        def stopwatch0 = persister.find { it.id == id && it.endTime == null }
        if(stopwatch0 == null) return null
        Instant endTime = Instant.now()
        def stopwatch1 = [
                id: UUID.randomUUID().toString(),
                startTime: stopwatch0.startTime,
                endTime: endTime,
                duration: ChronoUnit.MILLIS.between(stopwatch0.startTime, endTime),
                parentId: stopwatch0.id
        ]
        persister << stopwatch1
        stopwatch1
    }

    //TODO: this should either just return null (or equivalent (option?)) or throw an error
    def findStopwatch(String id) {
        persister.find { it.id == id }
    }

    def findChildren(String parentId) {
        if(parentId == null) return null
        persister.findAll { it.parentId == parentId }
    }


}
