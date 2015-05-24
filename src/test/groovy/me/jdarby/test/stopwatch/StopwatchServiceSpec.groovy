package me.jdarby.test.stopwatch

import me.jdarby.stopwatch.persisters.InMemoryList
import me.jdarby.stopwatch.StopwatchService
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

//TODO: expand on these tests, implement integration tests
class StopwatchServiceSpec extends Specification {

    def "start stopwatch"() {
        given:
        StopwatchService sws = new StopwatchService(new InMemoryList())
        Instant then = Instant.now()

        when:
        def stopwatch = sws.start()

        then:
        stopwatch.id != null && !stopwatch.id.empty
        stopwatch.startTime.isAfter(then)
    }

    def "stop stopwatch"() {
        given:
        StopwatchService sws = new StopwatchService(new InMemoryList())
        Instant then = Instant.now()
        Thread.sleep(1)
        def stopwatch0 = sws.start()
        //Is there a better way to do this?
        Thread.sleep(1000) //puppies died


        when:
        def stopwatch1 = sws.stop(stopwatch0.id)
        long controlMillis = ChronoUnit.MILLIS.between(then, Instant.now())

        then:
        stopwatch1.id != null && !stopwatch1.id.empty && stopwatch1.id != stopwatch0.id
        stopwatch1.startTime.isAfter(then)
        //This can likely be better
        stopwatch1.duration > controlMillis*0.80 && stopwatch1.duration < controlMillis*1.20
    }

    def "get stopwatch"() {
        given:
        StopwatchService sws = new StopwatchService(new InMemoryList())
        def stopwatch0 = sws.start()
        Instant then = Instant.now()

        when:
        def stopwatch1 = sws.findStopwatch(stopwatch0.id)

        then:
        stopwatch1 == stopwatch0
    }

    def "get child records"() {
        given:
        StopwatchService sws = new StopwatchService(new InMemoryList())
        def parent = sws.start()
        10.times {
            sws.stop(parent.id)
        }

        when:
        def children = sws.findChildren(parent.id)

        then:
        children.size == 10
        children.each { assert it.parentId == parent.id }
    }

}
