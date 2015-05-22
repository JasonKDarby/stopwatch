package me.jdarby.stopwatch

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import me.jdarby.stopwatch.renderers.MapRenderer

class StopwatchModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StopwatchService).in(Scopes.SINGLETON)
    }

}
