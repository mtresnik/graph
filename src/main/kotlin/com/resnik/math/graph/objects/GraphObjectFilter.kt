package com.resnik.math.graph.objects

interface GraphObjectFilter<ITEM> {

    fun isValid(type: ITEM): Boolean

}