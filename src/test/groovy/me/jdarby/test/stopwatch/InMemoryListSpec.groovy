package me.jdarby.test.stopwatch

import me.jdarby.stopwatch.StopwatchRecord
import me.jdarby.stopwatch.persisters.InMemoryList
import spock.lang.Specification

import java.time.Instant

/**
 * Created by jdarby on 5/22/15.
 */
class InMemoryListSpec extends Specification {

    InMemoryList inMemoryList = new InMemoryList()

    def "find a thing"() {
        given:
        StopwatchRecord initial = [id: '5', startTime: Instant.now()] as StopwatchRecord
        inMemoryList.addRecord(initial)
        when:
        def found = inMemoryList.getById('5')

        then:
        found == initial
    }

    def "find a bunch of things"() {
        given:
        def records = [
            [id: '5', startTime: Instant.now(), parentId: '8'] as StopwatchRecord,
            [id: '6', startTime: Instant.now(), parentId: '8'] as StopwatchRecord,
            [id: '7', startTime: Instant.now(), parentId: '8'] as StopwatchRecord
        ]
        records.each {
            inMemoryList.addRecord(it)
        }

        when:
        def found = inMemoryList.getChildrenByParentId('8')

        then:
        found == records
    }

}
