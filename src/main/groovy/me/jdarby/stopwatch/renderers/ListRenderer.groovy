package me.jdarby.stopwatch.renderers

import groovy.json.JsonBuilder
import ratpack.handling.Context
import ratpack.render.RendererSupport
import static MapRenderer.convertInstantsToISOStrings
import static MapRenderer.removeNullValues

/**
 * Created by jdarby on 5/21/15.
 */
class ListRenderer extends RendererSupport<List> {

    static List<Map> listOfMapsToRenderable(List<Map> list) {
        list.collect {
            convertInstantsToISOStrings(removeNullValues(it))
        }
    }

    @Override
    public void render(Context ctx, List list) throws Exception {
        JsonBuilder json = new JsonBuilder()
        json listOfMapsToRenderable(list)
        ctx.render json.toPrettyString()
    }

}
