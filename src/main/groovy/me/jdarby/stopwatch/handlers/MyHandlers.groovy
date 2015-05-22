package me.jdarby.stopwatch.handlers

import ratpack.handling.Context
import ratpack.handling.Handler

/**
 * Created by jdarby on 5/21/15.
 */
class MyHandlers {

    Handler contentTypeJson = { Context ctx ->
        ctx.response.contentType('application/json')
        ctx.next()
    } as Handler

}
