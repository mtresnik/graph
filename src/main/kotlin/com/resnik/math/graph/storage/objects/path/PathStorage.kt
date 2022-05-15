package com.resnik.math.graph.storage.objects.path

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.storage.FlaggableSerializer
import com.resnik.math.graph.storage.GraphConstants
import com.resnik.math.graph.storage.ItemizedLongStorable
import com.resnik.math.graph.storage.file.FileStorable
import com.resnik.math.graph.storage.file.HeaderDescribed
import com.resnik.math.graph.storage.objects.edge.EdgeStorage

class PathStorage(val edgeStorage: EdgeStorage) : ItemizedLongStorable<Path>(), FileStorable,
    HeaderDescribed<PathHeader> {

    override fun toString(value: Path): String {
        var retString = ""
        retString += GraphConstants.Keys.PATH
        retString += GraphConstants.Keys.SPACE
        save(value)
        // id
        retString += value.id
        retString += GraphConstants.Keys.SPACE
        // add edges
        retString += value.map { edge -> edge.id }.joinToString(GraphConstants.Keys.SPACE)
        retString += GraphConstants.Keys.SPACE
        // add misc values associated with the path
        retString += GraphConstants.Keys.END
        retString += GraphConstants.Keys.SPACE
        retString += value.values.joinToString(GraphConstants.Keys.SPACE)
        retString += FlaggableSerializer.flagsToString(value)
        return retString
    }

    override fun fromString(input: String): Path? {
        // ind, id, edges... STOP values... STOP flags...
        val inputs = input.split(GraphConstants.Keys.SPACE)
        // Id
        val id: Long = inputs[1].toLongOrNull() ?: return null
        // edges
        val stopIndices = inputs.indices.filter { index -> inputs[index] == GraphConstants.Keys.END }
        val lastEdgeIndex = if (stopIndices.isNotEmpty()) stopIndices[0] - 1 else inputs.lastIndex
        val edges = mutableListOf<Edge>()
        (2..lastEdgeIndex).forEach { index ->
            val edgeId = inputs[index].toLongOrNull() ?: return null
            edges.add(edgeStorage[edgeId] ?: return null)
        }

        // init
        val path = Path(edges, id = id)

        // values
        if (stopIndices.isNotEmpty()) {
            val values = mutableListOf<Double>()
            val startIndex = stopIndices[0] + 1
            val lastValueIndex = if (stopIndices.size >= 2) stopIndices[1] - 1 else inputs.lastIndex
            if (lastValueIndex <= 1) return null
            (startIndex..lastValueIndex).forEach { index ->
                if (inputs[index].isNotEmpty()) values.add(inputs[index].toDoubleOrNull() ?: return null)
            }
            path.values = values
        }
        // flags
        if (stopIndices.size >= 2) {
            val flagStartIndex = stopIndices[1] + 1
            val flags = FlaggableSerializer.flagsFromString(inputs, flagStartIndex, inputs.lastIndex)
            path.setFlags(flags)
        }
        return path
    }

    override fun canMap(input: String): Boolean {
        return input.startsWith(GraphConstants.Keys.PATH)
    }

    override fun getExtension(): String = GraphConstants.Keys.Extensions.PATH

    override fun getName(): String = GraphConstants.Names.PATHS

    override fun save(value: Path) {
        super.save(value)
        value.forEach { edge -> edgeStorage.save(edge) }
    }

    override fun getHeader(): PathHeader = PathHeader(size = this.size())

    public override fun clone(): PathStorage {
        val ret = PathStorage(this.edgeStorage.clone())
        this.forEach { path -> ret.save(path.clone()) }
        return ret
    }

}