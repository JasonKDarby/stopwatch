package me.jdarby.stopwatch.persisters

import me.jdarby.stopwatch.StopwatchRecord

/**
 * Created by jdarby on 5/22/15.
 */
interface Persister {

    def addRecord(StopwatchRecord stopwatchRecord)

    def getById(String id)

    def getChildrenByParentId(String id)

}
