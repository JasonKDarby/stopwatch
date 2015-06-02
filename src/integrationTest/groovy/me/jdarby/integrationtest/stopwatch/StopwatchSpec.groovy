package me.jdarby.integrationtest.stopwatch

import groovyx.net.http.RESTClient
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit
import spock.lang.Shared

class StopwatchSpec extends Specification {

    @Shared
    private def restUrl = "http://localhost:5050/api/"

    private RESTClient client = new RESTClient(restUrl)

    def "create a stopwatch start record"() {
        when:
        def localStartTime = Instant.now()
        def resp = client.post([:])
        def localDuration = ChronoUnit.SECONDS.between(localStartTime, Instant.now())
        def start = resp.data

        then:
        resp.contentType == 'application/json'
        resp.status == 200

        UUID.fromString(start.id)

        and: "remote start time is within my local start time and the response time"
        //can I do better than this?
        ChronoUnit.SECONDS.between(localStartTime, Instant.parse(start.startTime)) <= localDuration
    }

    def "create a stopwatch stop record"() {
        given:
        def start = client.post([:]).data

        when:
        def resp = client.post(path: "${start.id}")
        def stop = resp.data

        then:
        resp.contentType == 'application/json'
        resp.status == 200

        UUID.fromString(stop.id)
        stop.startTime == start.startTime
        ChronoUnit.SECONDS.between(Instant.parse(stop.startTime), Instant.parse(stop.endTime)) <= 1
        stop.duration <= 1000
    }

    def "get start and stop records"() {
        given:
        def start = client.post([:]).data
        def stop = client.post(path: "${start.id}").data

        when:
        def resp0 = client.get(path: "${start.id}")
        def resp1 = client.get(path: "${stop.id}")
        def start0 = resp0.data
        def stop0 = resp1.data

        then:
        resp0.contentType == 'application/json'
        resp1.contentType == 'application/json'
        resp0.status == 200
        resp1.status == 200

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
        def parent = client.post([:]).data
        10.times {
            client.post(path: "${parent.id}")
        }

        when:
        def children = client.get(path: "${parent.id}/children").data
        println children

        then:
        children.size == 10
        children.each { assert it.parentId == parent.id }
    }

}
