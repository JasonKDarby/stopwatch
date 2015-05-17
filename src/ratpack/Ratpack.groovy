import groovy.json.JsonBuilder
import me.jdarby.stopwatch.StopwatchModule
import me.jdarby.stopwatch.StopwatchService
import ratpack.groovy.template.MarkupTemplateModule

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        add MarkupTemplateModule
        add StopwatchModule
    }

    handlers { StopwatchService sws ->

        get {
            render groovyMarkupTemplate("stopwatch/stopwatch.gtpl")
        }

        assets "public"

        prefix("api") {

            handler(":id") {
                byMethod {
                    get {
                        Map stopwatch = sws.get(pathTokens["id"]) ?: clientError(404)
                        def builder = new JsonBuilder()
                        builder id: stopwatch.id,
                                startTime: stopwatch.startTime.toString(),
                                endTime: stopwatch?.endTime?.toString(),
                                duration: stopwatch?.duration
                        response.contentType('application/json')
                        render builder.toPrettyString()
                    }
                    post {
                        Map stopwatch = sws.stop(pathTokens["id"]) ?: clientError(404)
                        JsonBuilder builder = new JsonBuilder()
                        builder id: stopwatch.id,
                                startTime: stopwatch.startTime.toString(),
                                endTime: stopwatch.endTime.toString(),
                                duration: stopwatch.duration
                        response.contentType('application/json')
                        render builder.toPrettyString()
                    }
                }
            }

            handler {
                byMethod {
                    post {
                        Map stopwatch = sws.start()
                        def builder = new JsonBuilder()
                        builder id: stopwatch.id,
                                startTime: stopwatch.startTime.toString()
                        response.contentType('application/json')
                        render builder.toPrettyString()
                    }
                }
            }
        }
    }
}