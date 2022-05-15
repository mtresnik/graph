package com.resnik.math.graph.storage.objects.edge

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.PolyEdge
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.graph.storage.FlaggableSerializer
import com.resnik.math.graph.storage.GraphConstants
import com.resnik.math.graph.storage.ItemizedLongStorable
import com.resnik.math.graph.storage.file.FileStorable
import com.resnik.math.graph.storage.file.HeaderDescribed
import com.resnik.math.graph.storage.objects.vertex.VertexStorage

class EdgeStorage(val vertexStorage: VertexStorage) : ItemizedLongStorable<Edge>(), FileStorable,
    HeaderDescribed<EdgeHeader> {

    private fun toStringEdge(edge: Edge): String {
        var retString = ""
        retString += GraphConstants.Keys.EDGE
        retString += GraphConstants.Keys.SPACE
        save(edge)
        // id
        retString += edge.id
        retString += GraphConstants.Keys.SPACE
        // add vertices
        retString += edge.from.id
        retString += GraphConstants.Keys.SPACE
        retString += edge.to.id
        retString += GraphConstants.Keys.SPACE
        retString += edge.weight
        // add flags
        retString += FlaggableSerializer.flagsToString(edge)
        return retString
    }

    private fun fromStringEdge(input: String): Edge? {
        val inputs = input.split(GraphConstants.Keys.SPACE)
        // Id
        val id: Long = inputs[1].toLongOrNull() ?: return null
        // Vertices
        val fromId: Long = inputs[2].toLongOrNull() ?: return null
        val toId: Long = inputs[3].toLongOrNull() ?: return null
        val from: Vertex = vertexStorage[fromId] ?: return null
        val to: Vertex = vertexStorage[toId] ?: return null
        val weight: Double = inputs[4].toDoubleOrNull() ?: return null
        // Flags
        val flags = FlaggableSerializer.flagsFromString(inputs, 6, input.lastIndex)
        val ret = Edge(from, to, weight, id = id)
        ret.setFlags(flags)
        return ret
    }

    private fun fromStringPolyEdge(input: String): PolyEdge? {
        val inputs = input.split(GraphConstants.Keys.SPACE)
        // Id
        val id: Long = inputs[1].toLongOrNull() ?: return null

        var stopIndex = inputs.indexOf(GraphConstants.Keys.END)
        if (stopIndex == -1) stopIndex = inputs.size // if there aren't any flags
        val weightIndex = stopIndex - 1
        val lastVertexIndex = weightIndex - 1
        if (lastVertexIndex <= 1) return null
        val geometry = mutableListOf<Vertex>()
        (2..lastVertexIndex).forEach { index ->
            val currentVertexId = inputs[index].toLongOrNull() ?: return null
            val currentVertex = vertexStorage[currentVertexId] ?: return null
            geometry.add(currentVertex)
        }
        val weight = inputs[weightIndex].toDoubleOrNull() ?: return null

        val flagStartIndex = stopIndex + 1
        val flags = FlaggableSerializer.flagsFromString(inputs, flagStartIndex, input.lastIndex)
        val ret = PolyEdge(geometry, weight = weight, id = id)
        ret.setFlags(flags)
        return ret
    }

    private fun toStringPolyEdge(polyEdge: PolyEdge): String {
        var retString = ""
        retString += GraphConstants.Keys.POLYEDGE
        retString += GraphConstants.Keys.SPACE
        save(polyEdge)
        // id
        retString += polyEdge.id
        retString += GraphConstants.Keys.SPACE
        // add vertices
        retString += polyEdge.geometry.map { vertex -> vertex.id }.joinToString(GraphConstants.Keys.SPACE)
        retString += GraphConstants.Keys.SPACE
        retString += polyEdge.weight
        // add flags
        retString += FlaggableSerializer.flagsToString(polyEdge)
        return retString
    }

    override fun toString(value: Edge): String {
        if (value is PolyEdge) return toStringPolyEdge(value)
        return toStringEdge(value)
    }

    override fun fromString(input: String): Edge? {
        if (input.startsWith(GraphConstants.Keys.EDGE)) return fromStringEdge(input)
        if (input.startsWith(GraphConstants.Keys.POLYEDGE)) return fromStringPolyEdge(input)
        return null
    }

    override fun canMap(input: String): Boolean {
        return input.startsWith(GraphConstants.Keys.EDGE) || input.startsWith(GraphConstants.Keys.POLYEDGE)
    }

    override fun getExtension(): String = GraphConstants.Keys.Extensions.EDGE

    override fun getName(): String = GraphConstants.Names.EDGES

    override fun save(value: Edge) {
        super.save(value)
        if (value is PolyEdge) {
            value.geometry.forEach { vertex -> vertexStorage.save(vertex) }
        } else {
            value.from = vertexStorage.getOrSave(value.from)
            value.to = vertexStorage.getOrSave(value.to)
        }
    }

    override fun getHeader(): EdgeHeader = EdgeHeader(size = size())

    public override fun clone(): EdgeStorage {
        val clonedVertex = vertexStorage.clone()
        val ret = EdgeStorage(clonedVertex)
        this.forEach { edge -> ret.save(edge.clone()) }
        return ret
    }

}