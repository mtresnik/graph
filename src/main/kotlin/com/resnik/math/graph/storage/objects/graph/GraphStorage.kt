package com.resnik.math.graph.storage.objects.graph

import com.resnik.math.graph.storage.GraphConstants
import com.resnik.math.graph.storage.ItemizedLongStorable
import com.resnik.math.graph.storage.file.FileStorable
import com.resnik.math.graph.storage.file.HeaderDescribed
import com.resnik.math.graph.storage.objects.edge.EdgeStorage
import com.resnik.math.graph.storage.objects.path.PathStorage
import com.resnik.math.graph.storage.objects.vertex.VertexStorage
import java.io.*
import java.util.*

class GraphStorage(private var parentLocation: File = DEFAULT_PARENT) : FileStorable, HeaderDescribed<GraphFileHeader>,
    Cloneable {

    var vertexStorage = VertexStorage()
    var edgeStorage = EdgeStorage(vertexStorage)
    var pathStorage = PathStorage(edgeStorage)
    private val storeMap = mutableMapOf<String, ItemizedLongStorable<*>>()

    init {
        storeMap[GraphConstants.Keys.VERTEX] = vertexStorage
        storeMap[GraphConstants.Keys.EDGE] = edgeStorage
        storeMap[GraphConstants.Keys.POLYEDGE] = edgeStorage
        storeMap[GraphConstants.Keys.PATH] = pathStorage
    }

    override fun saveTo(file: File) {
        // Assume just the header is saved in the file
        this.parentLocation = file.parentFile
        val header = getHeader()
        vertexStorage.saveFromParent(parentLocation)
        edgeStorage.saveFromParent(parentLocation)
        pathStorage.saveFromParent(parentLocation)
        header.writeTo(FileOutputStream(file))
    }

    override fun loadFrom(file: File) {
        // Assume just the header is saved in the file
        val header = getHeader()
        header.readFrom(FileInputStream(file))
        loadFrom(header)
    }

    override fun writeTo(outputStream: OutputStream, close: Boolean) {
        // write graph header which just contains file locations
        vertexStorage.writeTo(outputStream, close = false)
        edgeStorage.writeTo(outputStream, close = false)
        pathStorage.writeTo(outputStream, close = false)
        if (close) outputStream.close()
    }

    override fun readFrom(inputStream: InputStream, close: Boolean) {
        // Use data in header to load different stores
        val scanner = Scanner(inputStream)
        val headers = storeMap.values
            .filterIsInstance<HeaderDescribed<*>>()
            .map { it.getHeader() }
        while (scanner.hasNextLine()) {
            val line = scanner.nextLine()
            val store = storeMap.values
                .firstOrNull { storable -> storable.canMap(line) }
            if (store?.canMap(line) == true) {
                store.consumeIfPossible(line)
            } else {
                // try using headers
                headers.firstOrNull { header -> header.canMap(line) }?.consumeIfPossible(line)
            }
        }
    }

    override fun getHeader(): GraphFileHeader {
        if (!parentLocation.exists()) parentLocation.mkdirs()
        val vertexLocation = vertexStorage.getFile(parentLocation)
        val edgesLocation = edgeStorage.getFile(parentLocation)
        val pathsLocation = pathStorage.getFile(parentLocation)
        return GraphFileHeader(vertexLocation, edgesLocation, pathsLocation)
    }

    override fun loadFrom(header: GraphFileHeader) {
        if (header.verticesLocation != null)
            vertexStorage.readFrom(FileInputStream(header.verticesLocation!!))
        if (header.edgesLocation != null)
            edgeStorage.readFrom(FileInputStream(header.edgesLocation!!))
        if (header.pathsLocation != null)
            pathStorage.readFrom(FileInputStream(header.pathsLocation!!))
    }

    override fun getExtension(): String = GraphConstants.Keys.Extensions.GRAPH

    override fun getName(): String = GraphConstants.Names.GRAPHS

    override fun getFile(parent: File): File {
        this.parentLocation = parent
        return super.getFile(parent)
    }

    public override fun clone(): GraphStorage {
        val new = GraphStorage(parentLocation = parentLocation)
        val clonedEdgeStorage = edgeStorage.clone()
        val vertexStorage = clonedEdgeStorage.vertexStorage
        // make sure all from vertices exist in vertex storage
        clonedEdgeStorage.forEach { edge ->
            edge.from = vertexStorage.getOrSave(edge.from)
            edge.to = vertexStorage.getOrSave(edge.to)
            edge.from.edges.add(edge)
        }
        // get all edges that have no destination and remove them
        new.vertexStorage = vertexStorage
        new.edgeStorage = clonedEdgeStorage
        return new
    }

    companion object {

        // Could replace with appdata here
        val DEFAULT_PARENT = File("graphs")

    }


}