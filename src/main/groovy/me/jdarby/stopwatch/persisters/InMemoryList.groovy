package me.jdarby.stopwatch.persisters

import javax.inject.Inject

/**
 * Created by jdarby on 5/22/15.
 */
class InMemoryList implements Persister {

    private List theList

    @Inject
    InMemoryList() {
        theList = []
    }

    @Override
    def leftShift(Object thing) {
        theList << thing
    }

    @Override
    def find(Closure c) {
        theList.find(c)
    }

    @Override
    def findAll(Closure c) {
        theList.findAll(c)
    }
}
