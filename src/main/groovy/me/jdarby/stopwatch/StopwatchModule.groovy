package me.jdarby.stopwatch

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import com.google.inject.TypeLiteral
import me.jdarby.stopwatch.persisters.AWSDynamoDBPersister
import me.jdarby.stopwatch.persisters.InMemoryList
import me.jdarby.stopwatch.renderers.MapRenderer

class StopwatchModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<StopwatchService<AWSDynamoDBPersister>>(){}).in(Scopes.SINGLETON)
        bind(AWSDynamoDBPersister).in(Scopes.SINGLETON)
    }

}
