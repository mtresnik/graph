package com.resnik.math.graph.mst

import com.resnik.math.graph.objects.PartiallyConnectedGraph
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import org.junit.jupiter.api.Test
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class TestMST {

    val endFile = File("C:\\Users\\Mike\\Desktop\\mst\\examples")

    private fun saveIfExists(image : BufferedImage, name : String, ext : String = "png") {
        if(!endFile.exists()) return
        ImageIO.write(image, ext, File(endFile, "$name.$ext"))
    }

    fun randomPoints(max:Int = 20): Array<ArrayPoint> {
        return Array(max) { ArrayPoint(Math.random(), Math.random()) }
    }

    @Test
    fun testKruskals() {
        val initialGraph = PartiallyConnectedGraph(*randomPoints(), numConnections = 10)
        val kruskalsMST = KruskalsMST(initialGraph)
        val mst = kruskalsMST.build()

        val collection = GraphCollection()
        collection.addGraph(initialGraph, Color.BLACK)
        collection.addGraph(mst, Color.RED)

        val image = collection.render()
        saveIfExists(image, "kruskals")
    }

    @Test
    fun testPrims() {
        val initialGraph = PartiallyConnectedGraph(*randomPoints(), numConnections = 10)
        val primsMST = PrimsMST(initialGraph)
        val mst = primsMST.build()

        val collection = GraphCollection()
        collection.addGraph(initialGraph, Color.BLACK)
        collection.addGraph(mst, Color.RED)

        val image = collection.render()
        saveIfExists(image, "prims")
    }

}