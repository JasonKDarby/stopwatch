import me.jdarby.stopwatch.StopwatchModule
import me.jdarby.stopwatch.StopwatchRecord
import me.jdarby.stopwatch.StopwatchService
import me.jdarby.stopwatch.renderers.StopwatchRecordListRenderer
import me.jdarby.stopwatch.renderers.StopwatchRecordRenderer
import ratpack.registry.Registries
import static ratpack.groovy.Groovy.ratpack

ratpack {

    bindings {
        add StopwatchModule
    }

    handlers { StopwatchService sws ->

        register(Registries.just(new StopwatchRecordRenderer()))
        register(Registries.just(new StopwatchRecordListRenderer()))

        assets "public"

        prefix("api") {

            handler(":id/children") {
                byMethod {
                    get {
                        List<StopwatchRecord> children = sws.findChildren(pathTokens["id"]) ?: clientError(404)
                        response.contentType('application/json')
                        render children
                    }
                }
            }

            handler(":id") {

                byMethod {
                    get {
                        StopwatchRecord stopwatch = sws.findStopwatch(pathTokens["id"]) ?: clientError(404)
                        response.contentType('application/json')
                        render stopwatch
                    }
                    post {
                        StopwatchRecord stopwatch = sws.stop(pathTokens["id"]) ?: clientError(404)
                        response.contentType('application/json')
                        render stopwatch
                    }
                }
            }

            handler {
                byMethod {
                    post {
                        StopwatchRecord stopwatch = sws.start()
                        response.contentType('application/json')
                        render stopwatch
                    }
                }
            }
        }
    }
}