package com.resnik.math.graph.storage.objects.vertex

import com.resnik.math.graph.objects.Vertex
import com.resnik.math.graph.storage.FlaggableSerializer
import com.resnik.math.graph.storage.GraphConstants
import com.resnik.math.graph.storage.ItemizedLongStorable
import com.resnik.math.graph.storage.file.FileStorable
import com.resnik.math.graph.storage.file.Header
import com.resnik.math.graph.storage.file.HeaderDescribed
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox

class VertexStorage : ItemizedLongStorable<Vertex>(), FileStorable, HeaderDescribed<VertexHeader> {

    override fun toString(value: Vertex): String {
        var retString = ""
        retString += GraphConstants.Keys.VERTEX
        retString += GraphConstants.Keys.SPACE
        save(value)
        // id
        retString += value.id
        retString += GraphConstants.Keys.SPACE
        // add points
        retString += value.values.joinToString(GraphConstants.Keys.SPACE)
        // add flags
        retString += FlaggableSerializer.flagsToString(value)
        return retString
    }

    override fun fromString(input: String): Vertex? {
        val inputs = input.split(GraphConstants.Keys.SPACE)
        // Id
        val id : Long = inputs[1].toLongOrNull() ?: return null

        var stopIndex = inputs.indexOf(GraphConstants.Keys.END)
        if(stopIndex == -1) stopIndex = inputs.size // if there aren't any flags
        val lastValueIndex = stopIndex - 1
        if(lastValueIndex <= 1) return null
        val values = mutableListOf<Double>()
        (2..lastValueIndex).forEach { index->
            values.add(inputs[index].toDoubleOrNull() ?: return null)
        }
        // add flags
        val flagStartIndex = stopIndex + 1
        val flags = FlaggableSerializer.flagsFromString(inputs, flagStartIndex, inputs.lastIndex)
        val ret = Vertex(values = values.toDoubleArray(), id = id)
        ret.setFlags(flags)
        return ret
    }

    override fun canMap(input: String): Boolean {
        return input.startsWith(GraphConstants.Keys.VERTEX)
    }

    override fun getExtension(): String = GraphConstants.Keys.Extensions.VERTEX

    override fun getName(): String = GraphConstants.Names.VERTICES

    override fun getHeader(): VertexHeader {
        if(size() == 0) {
            return VertexHeader(size = this.size(), boundingBox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(0.0, 0.0)))
        }
        return VertexHeader(size = this.size(), boundingBox = BoundingBox(*items().toTypedArray()))
    }

    // O(n)
    fun nearestNeighbor(to : ArrayPoint) : Vertex? {
        if(this.isEmpty()) return null
        return this.minByOrNull { vertex -> vertex.distanceTo(to) }
    }

    // O(n*k)
    fun kNearestNeighbors(to : ArrayPoint, k : Int) : List<Vertex>? {
        if(this.isEmpty()) return null
        if(this.size() < k) return null
        if(k < 0) return null
        if(k == 0) return emptyList()
        val tempList = this.toMutableList()
        val retList = mutableListOf<Vertex>()
        repeat(k) {
            val currIndex = tempList.indices.minByOrNull { index -> tempList[index].distanceTo(to) } ?: return null
            retList.add(tempList.removeAt(currIndex))
        }
        return retList
    }


}