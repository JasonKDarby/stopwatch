package me.jdarby.stopwatch

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import me.jdarby.stopwatch.handlers.MyHandlers

class StopwatchModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StopwatchService.class).in(Scopes.SINGLETON)
        bind(MyHandlers.class)
    }

}
