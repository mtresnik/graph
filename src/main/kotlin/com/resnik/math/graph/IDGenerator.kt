package com.resnik.math.graph

class IDGenerator {

    private var MAX_ID : Long = 0

    fun genId() : Long {
        MAX_ID++
        return MAX_ID
    }

    fun update(currId : Long) {
        if(currId > MAX_ID) {
            MAX_ID = currId
        }
    }

}