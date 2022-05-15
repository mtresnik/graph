package com.resnik.math.graph.storage.objects.graph

import com.resnik.math.graph.storage.GraphConstants
import com.resnik.math.graph.storage.file.Header
import java.io.File

class GraphFileHeader(
    var verticesLocation: File? = null,
    var edgesLocation: File? = null,
    var pathsLocation: File? = null
) : Header(GraphConstants.Keys.GRAPH) {

    override fun getMap(): Map<String, List<String>> {
        val retMap = mutableMapOf<String, List<String>>()
        if (verticesLocation != null) {
            retMap[GraphConstants.Keys.VERTEX] = listOf(verticesLocation!!.absolutePath)
            if (edgesLocation != null) {
                retMap[GraphConstants.Keys.EDGE] = listOf(edgesLocation!!.absolutePath)
                if (pathsLocation != null) {
                    retMap[GraphConstants.Keys.PATH] = listOf(pathsLocation!!.absolutePath)
                }
            }
        }
        return retMap
    }

    override fun loadFromMap(map: Map<String, List<String>>) {
        if (GraphConstants.Keys.VERTEX in map)
            verticesLocation = File(map[GraphConstants.Keys.VERTEX]!!.joinToString(GraphConstants.Keys.SPACE))
        if (GraphConstants.Keys.EDGE in map)
            edgesLocation = File(map[GraphConstants.Keys.EDGE]!!.joinToString(GraphConstants.Keys.SPACE))
        if (GraphConstants.Keys.PATH in map)
            pathsLocation = File(map[GraphConstants.Keys.PATH]!!.joinToString(GraphConstants.Keys.SPACE))
    }

    override fun toString(): String {
        return "GraphFileHeader(verticesLocation=$verticesLocation, edgesLocation=$edgesLocation, pathsLocation=$pathsLocation)"
    }


}