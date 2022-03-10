package com.resnik.math.graph.storage.objects.vertex

import com.resnik.math.graph.storage.GraphConstants
import com.resnik.math.graph.storage.GraphConstants.Common.SIZE
import com.resnik.math.graph.storage.GraphConstants.Common.BOUNDING_BOX
import com.resnik.math.graph.storage.file.Header
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox

class VertexHeader(var size : Int = 0,
                   var boundingBox : BoundingBox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(0.0, 0.0)) )
                   : Header(GraphConstants.Keys.VERTEX) {

    override fun getMap(): Map<String, List<String>> {
        val tempMap = mutableMapOf<String, List<String>>()
        tempMap[SIZE] = listOf(size.toString())
        tempMap[BOUNDING_BOX] = listOf(boundingBox.minX(), boundingBox.minY(), boundingBox.maxX(), boundingBox.maxY()).map { it.toString() }
        return tempMap
    }

    override fun loadFromMap(map: Map<String, List<String>>) {
        this.size = map[SIZE]?.first()?.toIntOrNull() ?: 0
        val boundingList = map[BOUNDING_BOX]
        if(boundingList != null && boundingList.size == 4) {
            val minX = boundingList[0].toDoubleOrNull() ?: return
            val minY = boundingList[1].toDoubleOrNull() ?: return
            val maxX = boundingList[2].toDoubleOrNull() ?: return
            val maxY = boundingList[3].toDoubleOrNull() ?: return
            this.boundingBox = BoundingBox(ArrayPoint(minX, minY), ArrayPoint(maxX, maxY))
        }
    }

}