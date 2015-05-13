package me.jdarby.test.stopwatch

import me.jdarby.stopwatch.StopwatchService
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

//TODO: expand on these tests, implement integration tests
class StopwatchServiceSpec extends Specification {

    def "start stopwatch"() {
        given:
        StopwatchService sws = new StopwatchService()

        when:
        def id = sws.start()

        then:
        id != null
        id != ''
        id.length() > 0
    }

    def "stop stopwatch"() {
        given:
        StopwatchService sws = new StopwatchService()
        String id = sws.start()
        LocalDateTime then = LocalDateTime.now()

        when:
        long stopwatchSeconds = sws.stop(id)
        long controlSeconds = ChronoUnit.SECONDS.between(then, LocalDateTime.now())

        then:
        stopwatchSeconds == controlSeconds
    }

    def "get stopwatch"() {
        given:
        StopwatchService sws = new StopwatchService()
        String id = sws.start()
        LocalDateTime then = LocalDateTime.now()

        when:
        Map stopwatch = sws.get(id)

        then:
        stopwatch.startTime == then
        stopwatch.endTime == null
    }

    def "delete stopwatch"() {
        given:
        StopwatchService sws = new StopwatchService()
        String id = sws.start()

        when:
        sws.delete(id)
        sws.get(id)

        then:
        thrown(AssertionError)
    }
}
