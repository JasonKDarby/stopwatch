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
      //TODO: cleanup index page
      render groovyMarkupTemplate("index.gtpl", title: "My Ratpack App")
    }

    handler("start") {
      byMethod {
        post {
          String id = sws.start()
          JsonBuilder builder = new JsonBuilder()
          builder id: id
          render builder.toPrettyString()
        }
      }
    }

    handler(":id") {
      byMethod {
        get {
          def id = pathTokens["id"]
          Map stopwatch = sws.get(id)
          JsonBuilder builder = new JsonBuilder()
          builder startTime: stopwatch.startTime.toString(),
                  endTime: stopwatch?.endTime?.toString()
          render builder.toPrettyString()
        }

        delete { ctx ->
          def id = pathTokens["id"]
          sws.delete(id)
          ctx.response.send()
        }
      }
    }

    handler("stop/:id") {
      byMethod {
        post {
          def id = pathTokens["id"]
          long duration = sws.stop(id)
          JsonBuilder builder = new JsonBuilder()
          builder seconds: duration
          render builder.toPrettyString()
        }
      }
    }

    assets "public"
  }
}
