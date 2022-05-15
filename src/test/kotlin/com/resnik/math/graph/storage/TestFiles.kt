package com.resnik.math.graph.storage

import com.resnik.math.graph.TestSaveDelegate
import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.graph.storage.objects.graph.GraphStorage
import org.junit.Test
import java.io.File

class TestFiles : TestSaveDelegate() {

    @Test
    fun testVert1() {
        if (!SAVE) return
        val fileLocation = "C:\\Users\\Mike\\Desktop\\temp"
        val parent = File(fileLocation)
        if (!parent.exists()) return

        val graphStorage = GraphStorage()
        val vert1 = Vertex(0.5, 1.0)
        vert1.setFlags(listOf(1, 5, 11))
        val vert2 = Vertex(0.1, 2.0)
        val vert3 = Vertex(11.0, 2.0)
        vert3.setFlags(listOf(12))
        graphStorage.vertexStorage.save(vert1)
        graphStorage.vertexStorage.save(vert2)
        graphStorage.vertexStorage.save(vert3)

        val edge1 = Edge(vert1, vert2)
        val edge2 = Edge(vert2, vert3)
        val edge3 = Edge(vert3, vert1)
        graphStorage.edgeStorage.saveAll(listOf(edge1, edge2, edge3))

        graphStorage.vertexStorage.saveFromParent(parent)

    }

    @Test
    fun testFile1() {
        if (!SAVE) return
        val fileLocation = "C:\\Users\\Mike\\Desktop\\temp"
        val parent = File(fileLocation)
        if (!parent.exists()) return

        val graphStorage = GraphStorage()
        val vert1 = Vertex(0.5, 1.0)
        vert1.setFlags(listOf(1, 5, 11))
        val vert2 = Vertex(0.1, 2.0)
        val vert3 = Vertex(11.0, 2.0)
        vert3.setFlags(listOf(12))
        graphStorage.vertexStorage.save(vert1)
        graphStorage.vertexStorage.save(vert2)
        graphStorage.vertexStorage.save(vert3)

        val edge1 = Edge(vert1, vert2)
        val edge2 = Edge(vert2, vert3)
        val edge3 = Edge(vert3, vert1)
        graphStorage.edgeStorage.saveAll(listOf(edge1, edge2, edge3))

        graphStorage.saveFromParent(parent)

    }

    @Test
    fun testPath1() {
        if (!SAVE) return
        val fileLocation = "C:\\Users\\Mike\\Desktop\\temp\\with two  spaces"
        val parent = File(fileLocation)
        if (!parent.exists()) return

        val graphStorage = GraphStorage()
        val vert1 = Vertex(0.5, 1.0)
        vert1.setFlags(listOf(1, 5, 11))
        val vert2 = Vertex(0.1, 2.0)
        val vert3 = Vertex(11.0, 2.0)
        vert3.setFlags(listOf(12))
        graphStorage.vertexStorage.save(vert1)
        graphStorage.vertexStorage.save(vert2)
        graphStorage.vertexStorage.save(vert3)

        val edge1 = Edge(vert1, vert2)
        val edge2 = Edge(vert2, vert3)
        val edge3 = Edge(vert3, vert1)
        graphStorage.edgeStorage.saveAll(listOf(edge1, edge2, edge3))

        val path = Path(listOf(edge1, edge2, edge3))
        path.setFlags(listOf(100L))
        path.values.add(0.5)
        graphStorage.pathStorage.save(path)

        graphStorage.saveFromParent(parent)

    }

    @Test
    fun testWithSpaces() {
        if (!SAVE) return
        val fileLocation = "C:\\Users\\Mike\\Desktop\\temp\\with two  spaces"
        val parent = File(fileLocation)
        if (!parent.exists()) return
        val graphStorage = GraphStorage()
        graphStorage.loadFromParent(parent)
        val header = graphStorage.getHeader()
        println(header.toString())
    }

}