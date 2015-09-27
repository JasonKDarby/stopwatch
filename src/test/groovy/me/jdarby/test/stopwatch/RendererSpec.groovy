package me.jdarby.test.stopwatch

import me.jdarby.stopwatch.renderers.ListRenderer
import me.jdarby.stopwatch.renderers.MapRenderer
import spock.lang.Shared

import static me.jdarby.stopwatch.renderers.MapRenderer.convertInstantsToISOStrings
import static me.jdarby.stopwatch.renderers.MapRenderer.removeNullValues
import ratpack.groovy.test.embed.GroovyEmbeddedApp
import spock.lang.Specification

import java.time.Instant

/**
 * Created by jdarby on 5/21/15.
 */
class RendererSpec extends Specification {

    def "removeNullValues removes Map entries with null values"() {
        given:
        def mapWithNullValues = [a: null, b: 'b', c: null]

        when:
        def mapWithoutNullValues = removeNullValues(mapWithNullValues)

        then:
        mapWithoutNullValues == [b: 'b']
    }

    def "renderers fit into handlers properly"() {
        given:
        def app = GroovyEmbeddedApp.of {
            handlers {
                register {
                    add renderer
                }
                get {
                    render(renderable)
                }
            }
        }

        when:
        def response
        app.test { httpClient ->
            response = httpClient.get()
        }

        then:
        response.status.code == 200
        response.body.text == expectedText

        where:
        renderable               | expectedText                                           | renderer
        [a: 'a', b: 1, c: null]  | '{\n    "a": "a",\n    "b": 1\n}'                      | new MapRenderer()
        [[a: 'a', b: 1, c: null]]| '[\n'+s4+'{\n'+s8+'"a": "a",\n'+s8+'"b": 1\n'+s4+'}\n]'| new ListRenderer()
    }

    @Shared def s4 = ' '*4
    @Shared def s8 = s4 + s4

    def "convertInstantsToISOString writes understandable times"() {
        given:
        def now = Instant.now()
        def times = [a: now]

        when:
        def result = convertInstantsToISOStrings(times)

        then:
        result == [a: now.toString()]
    }
}
