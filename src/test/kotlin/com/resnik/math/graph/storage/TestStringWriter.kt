package com.resnik.math.graph.storage

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.graph.storage.objects.edge.EdgeStorage
import com.resnik.math.graph.storage.objects.path.PathStorage
import com.resnik.math.graph.storage.objects.vertex.VertexStorage
import org.junit.Test

class TestStringWriter {

    @Test
    fun testVertexStorage() {
        val vert1 = Vertex(0.5, 1.0)
        vert1.setFlags(listOf(1, 5, 11))
        val vertexStorage = VertexStorage()
        vertexStorage.save(vert1)
        val stringRep = vertexStorage.toString(vert1)
        println(stringRep)
        val parsedRep = vertexStorage.fromString(stringRep)
        println("after parse:")
        val afterParse = vertexStorage.toString(parsedRep!!)
        println(afterParse)
        assert(stringRep == afterParse)
    }

    @Test
    fun testEdgeStorage() {
        val vert1 = Vertex(0.5, 1.0)
        vert1.setFlags(listOf(1, 5, 11))
        val vert2 = Vertex(1.5, -1.0)
        val vertexStorage = VertexStorage()
        vertexStorage.save(vert1)
        vertexStorage.save(vert2)
        println(vertexStorage.toString(vert1))
        println(vertexStorage.toString(vert2))
        val edgeStorage = EdgeStorage(vertexStorage)
        val edge1 = Edge(vert1, vert2, weight = 10.5)
        edge1.setFlags(listOf(2, 5, 10))
        edgeStorage.save(edge1)
        val stringRep = edgeStorage.toString(edge1)
        println(stringRep)
        val parsedRep = edgeStorage.fromString(stringRep)
        val afterParse = edgeStorage.toString(parsedRep!!)
        println("after parse:")
        println(afterParse)
        assert(stringRep == afterParse)
    }

    @Test
    fun testPathParse() {
        val vert1 = Vertex(0.5, 1.0)
        vert1.setFlags(listOf(1, 5, 11))
        val vert2 = Vertex(1.5, -1.0)
        val vert3 = Vertex(5.0, 3.0)
        vert3.setFlags(listOf(10, 20, 14))
        val edge1 = Edge(vert1, vert2)
        val edge2 = Edge(vert2, vert1)

        val path1 = Path(listOf(edge1, edge2))
        path1.values = mutableListOf(1.0, 2.0)
        path1.setFlags(listOf(1, 2, 5))

        val vertexStorage = VertexStorage()
        val edgeStorage = EdgeStorage(vertexStorage)
        val pathStorage = PathStorage(edgeStorage)

        val stringRep = pathStorage.toString(path1)
        println(stringRep)
        println("after parse:")
        val parsedRep = pathStorage.fromString(stringRep)
        val afterParse = pathStorage.toString(parsedRep!!)
        println(afterParse)
    }

}