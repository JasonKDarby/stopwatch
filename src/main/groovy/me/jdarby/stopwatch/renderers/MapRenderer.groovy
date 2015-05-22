package me.jdarby.stopwatch.renderers

import groovy.json.JsonBuilder
import ratpack.handling.Context
import ratpack.render.RendererSupport

import java.time.Instant

/**
 * Created by jdarby on 5/21/15.
 */
class MapRenderer extends RendererSupport<Map> {

    static Map removeNullValues(Map map) {
        map.findAll { key, value -> value != null }
    }

    static Map convertInstantsToISOStrings(Map map) {
        map.collectEntries { key, value -> [(key): value.class == Instant ? value.toString() : value] }
    }

    @Override
    public void render(Context ctx, Map map) throws Exception {
        map = convertInstantsToISOStrings(removeNullValues(map))
        JsonBuilder json = new JsonBuilder()
        json map
        ctx.render json.toPrettyString()
    }

}
