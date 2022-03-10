package com.resnik.math.graph.storage.objects.edge

import com.resnik.math.graph.storage.GraphConstants
import com.resnik.math.graph.storage.GraphConstants.Common.SIZE
import com.resnik.math.graph.storage.file.Header

class EdgeHeader(var size : Int = 0) : Header(GraphConstants.Keys.EDGE) {

    override fun getMap(): Map<String, List<String>> {
        val tempMap = mutableMapOf<String, List<String>>()
        tempMap[SIZE] = listOf(size.toString())
        return tempMap
    }

    override fun loadFromMap(map: Map<String, List<String>>) {
        this.size = map[SIZE]?.first()?.toIntOrNull() ?: 0
    }

}