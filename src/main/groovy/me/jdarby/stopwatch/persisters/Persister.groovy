package me.jdarby.stopwatch.persisters

/**
 * Created by jdarby on 5/22/15.
 */
interface Persister {

    def leftShift(def thing)

    def find(Closure c)

    def findAll(Closure c)

}
