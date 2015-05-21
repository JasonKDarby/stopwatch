import clojure.java.api.Clojure
import clojure.lang.IFn
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

            handler(":id/children") {
                byMethod {
                    get {
                        List children = sws.findChildren(pathTokens["id"]) ?: clientError(404)
                        def builder = new JsonBuilder()
                        builder children.collect { stopwatch ->
                            [
                                    id: stopwatch.id,
                                    startTime: stopwatch.startTime.toString(),
                                    endTime: stopwatch.endTime.toString(),
                                    duration: stopwatch.duration,
                                    parentId: stopwatch.parentId
                            ]
                        }
                        response.contentType('application/json')
                        render builder.toPrettyString()
                    }
                }
            }

            handler(":id") {

                byMethod {
                    get {
                        Map stopwatch = sws.findStopwatch(pathTokens["id"]) ?: clientError(404)
                        def builder = new JsonBuilder()
                        builder id: stopwatch.id,
                                startTime: stopwatch.startTime.toString(),
                                endTime: stopwatch?.endTime?.toString(),
                                duration: stopwatch?.duration,
                                parentId: stopwatch?.parentId
                        IFn require = Clojure.var("clojure.core", "require")
                        require.invoke(Clojure.read("me.jdarby.Example"))
                        IFn test = Clojure.var("me.jdarby.Example", "-hello")
                        println "Clojure result: "+test.invoke('Jason')
                        response.contentType('application/json')
                        render builder.toPrettyString()
                    }
                    post {
                        Map stopwatch = sws.stop(pathTokens["id"]) ?: clientError(404)
                        JsonBuilder builder = new JsonBuilder()
                        builder id: stopwatch.id,
                                startTime: stopwatch.startTime.toString(),
                                endTime: stopwatch.endTime.toString(),
                                duration: stopwatch.duration,
                                parentId: stopwatch.parentId
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