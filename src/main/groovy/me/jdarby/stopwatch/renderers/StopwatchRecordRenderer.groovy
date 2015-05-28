package me.jdarby.stopwatch.renderers

import groovy.json.JsonBuilder
import me.jdarby.stopwatch.StopwatchRecord
import ratpack.handling.Context
import ratpack.render.RendererSupport

import static me.jdarby.stopwatch.renderers.MapRenderer.convertInstantsToISOStrings
import static me.jdarby.stopwatch.renderers.MapRenderer.removeNullValues

/**
 * Created by jdarby on 5/27/15.
 */
class StopwatchRecordRenderer extends RendererSupport<StopwatchRecord> {

    @Override
    public void render(Context ctx, StopwatchRecord stopwatchRecord) throws Exception {
        Map stopwatchRecordMap = [
            id: stopwatchRecord.id,
            startTime: stopwatchRecord.startTime,
            endTime: stopwatchRecord.endTime,
            duration: stopwatchRecord.duration,
            parentId: stopwatchRecord.parentId
        ]
        stopwatchRecordMap = removeNullValues(stopwatchRecordMap)
        stopwatchRecordMap = convertInstantsToISOStrings(stopwatchRecordMap)

        JsonBuilder json = new JsonBuilder()
        json stopwatchRecordMap
        ctx.render json.toPrettyString()
    }

}
