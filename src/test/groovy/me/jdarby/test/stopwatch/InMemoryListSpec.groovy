package me.jdarby.test.stopwatch

import me.jdarby.stopwatch.persisters.InMemoryList
import spock.lang.Specification

/**
 * Created by jdarby on 5/22/15.
 */
class InMemoryListSpec extends Specification {

    InMemoryList inMemoryList = new InMemoryList()

    def "find a thing"() {
        given:
        inMemoryList << 5

        when:
        def found = inMemoryList.find { it == 5 }

        then:
        found == 5
    }

    def "find a bunch of things"() {
        given:
        inMemoryList << 5
        inMemoryList << 6
        inMemoryList << 7

        when:
        def found = inMemoryList.findAll { it <= 6 }

        then:
        found == [5, 6]
    }

}
