package me.jdarby.stopwatch

import groovy.transform.Immutable

import java.time.Instant

/**
 * Created by jdarby on 5/24/15.
 */
@Immutable(knownImmutableClasses = [Instant])
class StopwatchRecord {

    String id
    Instant startTime
    Instant endTime
    Long duration
    String parentId

}
