package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.provider.BoundedGraphProvider
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import org.junit.jupiter.api.Test
import java.awt.Color

class TestBFS : TestGraphRenderer() {

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
        val start = vertices.random()
        val dest = vertices.random()

        val algorithm = BFS(GAParams(start, dest))
        val visitedListener = VisitRecorder()
        algorithm.addListener(visitedListener)
        val path = algorithm.evaluate()
        renderIfSet(graph, start, dest, path, visitedListener)
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
        val start = vertices.random()
        val dest = vertices.random()

        val algorithm = BFS(GAParams(start, dest))
        val visitedListener = VisitRecorder()
        algorithm.addListener(visitedListener)
        val path = algorithm.evaluate()
        renderIfSet(graph, start, dest, path, visitedListener)
    }

}