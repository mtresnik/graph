package com.resnik.math.graph.storage

interface StringStorable<ITEM> : Storable, Cloneable {

    fun toString(value : ITEM) : String

    fun fromString(input : String) : ITEM?

    fun canMap(input : String) : Boolean

    fun consumeIfPossible(input: String)

}