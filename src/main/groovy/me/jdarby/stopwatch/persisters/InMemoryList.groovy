package me.jdarby.stopwatch.persisters

import me.jdarby.stopwatch.StopwatchRecord

import javax.inject.Inject

/**
 * Created by jdarby on 5/22/15.
 */
class InMemoryList implements Persister {

    private List<StopwatchRecord> theList

    @Inject
    InMemoryList() {
        theList = []
    }

    @Override
    def addRecord(StopwatchRecord stopwatchRecord) {
        theList << stopwatchRecord
    }

    @Override
    def getById(String id) {
        theList.find { it.id == id }
    }

    @Override
    def getChildrenByParentId(String id) {
        theList.findAll { it.parentId == id }
    }
}
