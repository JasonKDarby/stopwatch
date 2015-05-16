package me.jdarby.integrationtest.stopwatch

import groovyx.net.http.RESTClient
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import geb.spock.GebReportingSpec
import spock.lang.Shared

class StopwatchSpec extends GebReportingSpec {

    @Shared
    private def restUrl = "${browser.baseUrl}api/"

    private RESTClient client = new RESTClient(restUrl)

    def "create a stopwatch start record"() {
        when:
        def resp = client.post([:])
        def start = resp.data

        then:
        resp.contentType == 'application/json'
        resp.status == 200

        UUID.fromString(start.id)
        ChronoUnit.SECONDS.between(LocalDateTime.parse(start.startTime), LocalDateTime.now()) <= 1
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
        ChronoUnit.SECONDS.between(LocalDateTime.parse(stop.startTime), LocalDateTime.parse(stop.endTime)) <= 1
        stop.duration <= 1000
    }

    def "get start and stop records"() {
        given:
        def start = client.post([:]).data
        def stop = client.post(path: "${start.id}").data

        when:
        //def something = [start, stop].collect { record -> client.get(path: "${record.id}").data }
        //def resp0, resp1 = something
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
        LocalDateTime.parse(start0.startTime)
        LocalDateTime.parse(stop0.startTime)
        start0.endTime == null
        LocalDateTime.parse(stop0.endTime)
        start0.duration == null
        stop0.duration <= 1000
    }

}