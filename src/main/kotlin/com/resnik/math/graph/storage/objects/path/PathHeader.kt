package com.resnik.math.graph.storage.objects.path

import com.resnik.math.graph.storage.GraphConstants
import com.resnik.math.graph.storage.GraphConstants.Common.SIZE
import com.resnik.math.graph.storage.file.Header

class PathHeader(var size: Int = 0) : Header(GraphConstants.Keys.PATH) {

    override fun getMap(): Map<String, List<String>> {
        val tempMap = mutableMapOf<String, List<String>>()
        tempMap[SIZE] = listOf(size.toString())
        return tempMap
    }

    override fun loadFromMap(map: Map<String, List<String>>) {
        this.size = map[SIZE]?.first()?.toIntOrNull() ?: 0
    }

}