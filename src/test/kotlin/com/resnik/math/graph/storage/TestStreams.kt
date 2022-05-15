package com.resnik.math.graph.storage

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.graph.storage.objects.edge.EdgeStorage
import com.resnik.math.graph.storage.objects.vertex.VertexStorage
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class TestStreams {

    @Test
    fun testVertex1() {
        val vert1 = Vertex(0.5, 1.0)
        vert1.setFlags(listOf(1, 5, 11))
        val vert2 = Vertex(0.1, 2.0)
        val vert3 = Vertex(11.0, 2.0)
        vert3.setFlags(listOf(12))
        val vertexStorage = VertexStorage()
        vertexStorage.save(vert1)
        vertexStorage.save(vert2)
        vertexStorage.save(vert3)

        val outputStream = ByteArrayOutputStream()
        vertexStorage.writeTo(outputStream)
        val stringRep = outputStream.toString()
        println(stringRep)

        val inputStream = ByteArrayInputStream(stringRep.toByteArray())
        val vertexStorage2 = VertexStorage()
        vertexStorage2.readFrom(inputStream)
        val outputStream2 = ByteArrayOutputStream()
        vertexStorage2.writeTo(outputStream2)
        val stringRep2 = outputStream2.toString()
        println("after parse:")
        println(stringRep2)
    }

    @Test
    fun testEdgeStream() {
        val vert1 = Vertex(0.5, 1.0)
        vert1.setFlags(listOf(1, 5, 11))
        val vert2 = Vertex(0.1, 2.0)
        val vert3 = Vertex(11.0, 2.0)
        vert3.setFlags(listOf(12))
        val vertexStorage = VertexStorage()
        vertexStorage.save(vert1)
        vertexStorage.save(vert2)
        vertexStorage.save(vert3)

        val edge1 = Edge(vert1, vert2)
        val edge2 = Edge(vert2, vert3)
        val edge3 = Edge(vert3, vert1)
        val edgeStorage = EdgeStorage(vertexStorage)
        edgeStorage.saveAll(listOf(edge1, edge2, edge3))

        val outputStream = ByteArrayOutputStream()
        edgeStorage.writeTo(outputStream)
        val stringRep = outputStream.toString()
        println(stringRep)

        val inputStream = ByteArrayInputStream(stringRep.toByteArray())
        val edgeStorage2 = EdgeStorage(vertexStorage)
        edgeStorage2.readFrom(inputStream)
        val outputStream2 = ByteArrayOutputStream()
        edgeStorage2.writeTo(outputStream2)
        val stringRep2 = outputStream2.toString()
        println("after parse:")
        println(stringRep2)
    }

}