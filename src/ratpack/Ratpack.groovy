import me.jdarby.stopwatch.StopwatchModule
import me.jdarby.stopwatch.StopwatchRecord
import me.jdarby.stopwatch.StopwatchService
import me.jdarby.stopwatch.renderers.StopwatchRecordListRenderer
import me.jdarby.stopwatch.renderers.StopwatchRecordRenderer
import static ratpack.groovy.Groovy.ratpack

ratpack {

    bindings {
        module StopwatchModule
    }

    handlers { StopwatchService sws ->

        register {
            add new StopwatchRecordRenderer()
            add new StopwatchRecordListRenderer()
        }

        fileSystem("public") {
            it.files()
        }

        prefix("api") {

            get(":id/children") {
                List<StopwatchRecord> children = sws.findChildren(pathTokens["id"]) ?: clientError(404)
                response.contentType('application/json')
                render children
            }

            path(":id") {
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

            post {
                StopwatchRecord stopwatch = sws.start()
                response.contentType('application/json')
                render stopwatch
            }
        }
    }
}