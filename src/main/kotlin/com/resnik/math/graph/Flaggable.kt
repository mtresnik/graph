package com.resnik.math.graph

interface Flaggable {

    fun setFlags(flags: Collection<Long>)

    fun getFlags(): Set<Long>

    fun clearFlags()

    operator fun contains(flag: Long): Boolean = flag in getFlags()

}