import groovy.json.JsonBuilder
import me.jdarby.stopwatch.StopwatchModule
import me.jdarby.stopwatch.StopwatchService
import me.jdarby.stopwatch.renderers.ListRenderer
import me.jdarby.stopwatch.renderers.MapRenderer
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.registry.Registries

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        add MarkupTemplateModule
        add StopwatchModule
    }

    handlers { StopwatchService sws ->

        register(Registries.just(new MapRenderer()))
        register(Registries.just(new ListRenderer()))

        get {
            render groovyMarkupTemplate("stopwatch/stopwatch.gtpl")
        }

        assets "public"

        prefix("api") {

            handler(":id/children") {
                byMethod {
                    get {
                        List children = sws.findChildren(pathTokens["id"]) ?: clientError(404)
                        response.contentType('application/json')
                        render children
                    }
                }
            }

            handler(":id") {

                byMethod {
                    get {
                        Map stopwatch = sws.findStopwatch(pathTokens["id"]) ?: clientError(404)
                        response.contentType('application/json')
                        render stopwatch
                    }
                    post {
                        Map stopwatch = sws.stop(pathTokens["id"]) ?: clientError(404)
                        response.contentType('application/json')
                        render stopwatch
                    }
                }
            }

            handler {
                byMethod {
                    post {
                        Map stopwatch = sws.start()
                        response.contentType('application/json')
                        render stopwatch
                    }
                }
            }
        }
    }
}