package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.provider.BoundedGraphProvider
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import org.junit.jupiter.api.Test
import java.awt.Color

class TestBFS {

    @Test
    fun testBFSRandom() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 20
        val height = 20
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()

        val collection = GraphCollection()
        collection.pointRadius = 10
        collection.lineStroke = 1.0f
        collection.addGraph(graph, Color.BLUE)

        val vertices = graph.storage.vertexStorage.toList()
        val origin = vertices.random()
        val dest = vertices.random()

        val algorithm = BFS(GAParams(origin, dest))
        val path = algorithm.evaluate()
        println(path)
        collection.addPoint(origin, color = Color.RED)
        collection.addPoint(dest, color = Color.RED)
        collection.addPath(path, Color.RED)
        collection.render()
    }

    @Test
    fun testBFSRandomVisited() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 50
        val height = 50
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()

        val collection = GraphCollection()
        collection.pointRadius = 10
        collection.lineStroke = 1.0f
        collection.addGraph(graph, Color.BLUE)

        val vertices = graph.storage.vertexStorage.toList()
        val minVertex = vertices.random()
        val maxVertex = vertices.random()

        val algorithm = BFS(GAParams(minVertex, maxVertex))
        val visitedListener = VisitRecorder()
        algorithm.addListener(visitedListener)
        val path = algorithm.evaluate()
        collection.addPoints(visitedListener.toList(), Color.GREEN)
        collection.addPoint(minVertex, color = Color.RED)
        collection.addPoint(maxVertex, color = Color.RED)
        collection.addPath(path, Color.RED)
        collection.addPoint(minVertex, color = Color.YELLOW)
        collection.render()
    }

}