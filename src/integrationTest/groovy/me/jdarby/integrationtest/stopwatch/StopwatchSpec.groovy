package me.jdarby.integrationtest.stopwatch

import groovy.json.JsonSlurper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

class StopwatchSpec extends Specification {

    @AutoCleanup def aut = new GroovyRatpackMainApplicationUnderTest()

    @Delegate TestHttpClient client0 = TestHttpClient.testHttpClient(aut)

    def slurper = JsonSlurper.newInstance()
    def json = { slurper.parseText(it) }

    def "create a stopwatch start record"() {
        when:
        def localStartTime = Instant.now()
        post 'api'
        def start = json(response.body.text)
        def localDuration = ChronoUnit.SECONDS.between(localStartTime, Instant.now())

        then:
        response.headers.'content-type' == 'application/json'
        response.status.code == 200

        UUID.fromString(start.id)

        and: "remote start time is within my local start time and the response time"
        //can I do better than this?
        ChronoUnit.SECONDS.between(localStartTime, Instant.parse(start.startTime)) <= localDuration
    }

    def "create a stopwatch stop record"() {
        given:
        post 'api'
        def start = json(response.body.text)

        when:
        post "api/${start.id}"
        def stop = json(response.body.text)

        then:
        response.headers.'content-type' == 'application/json'
        response.status.code == 200

        UUID.fromString(stop.id)
        stop.startTime == start.startTime
        ChronoUnit.SECONDS.between(Instant.parse(stop.startTime), Instant.parse(stop.endTime)) <= 1
        stop.duration <= 1000
    }

    def "get start and stop records"() {
        given:
        post 'api'
        def start = json(response.body.text)
        post "api/${start.id}"
        def stop = json(response.body.text)

        when:
        get "api/${start.id}"
        def resp0 = response
        def start0 = json(response.body.text)
        get "api/${stop.id}"
        def resp1 = response
        def stop0 = json(response.body.text)

        then:
        resp0.headers.'content-type' == 'application/json'
        resp1.headers.'content-type' == 'application/json'
        resp0.status.code == 200
        resp1.status.code == 200

        UUID.fromString(start0.id)
        UUID.fromString(stop0.id)
        Instant.parse(start0.startTime)
        Instant.parse(stop0.startTime)
        start0.endTime == null
        Instant.parse(stop0.endTime)
        start0.duration == null
        stop0.duration <= 1000
    }

    def "get children of a stopwatch"() {
        given:
        post 'api'
        def parent = json(response.body.text)
        10.times {
            post "api/${parent.id}"
        }

        when:
        get "api/${parent.id}/children"
        def children = json(response.body.text)

        then:
        children.size == 10
        children.each { assert it.parentId == parent.id }
    }

}
