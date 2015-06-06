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

    StopwatchRecord start() {
        StopwatchRecord stopwatch = [id: UUID.randomUUID().toString(), startTime: Instant.now()]
        persister.addRecord(stopwatch)
        stopwatch
    }

    //TODO: return value is in milliseconds for no good reason
    StopwatchRecord stop(String id) {
        def stopwatch0 = persister.getById(id)
        if(stopwatch0 == null) return null
        Instant endTime = Instant.now()
        def stopwatch1 = [
                id: UUID.randomUUID().toString(),
                startTime: stopwatch0.startTime,
                endTime: endTime,
                duration: ChronoUnit.MILLIS.between(stopwatch0.startTime, endTime),
                parentId: stopwatch0.id
        ]
        persister.addRecord(stopwatch1 as StopwatchRecord)
        stopwatch1
    }

    //TODO: this should either just return null (or equivalent (option?)) or throw an error
    StopwatchRecord findStopwatch(String id) {
        persister.getById(id)
    }

    List<StopwatchRecord> findChildren(String parentId) {
        if(parentId == null) return null
        persister.getChildrenByParentId(parentId)
    }


}
