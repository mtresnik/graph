package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.provider.BoundedGraphProvider
import com.resnik.math.graph.objects.provider.RandomPruneGraphProvider
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import org.junit.jupiter.api.Test
import java.awt.Color

class TestDFS {

    @Test
    fun testDFS1() {
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
        val minVertex = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val maxVertex = vertices.minByOrNull {  vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(minVertex != null)
        minVertex!!
        assert(maxVertex != null)
        maxVertex!!

        val algorithm = DFS(minVertex, maxVertex, graph)
        val path = algorithm.evaluate()
        collection.addPoint(minVertex, color = Color.RED)
        collection.addPoint(maxVertex, color = Color.RED)
        collection.addPath(path, Color.RED)
        collection.addPoint(minVertex, color = Color.YELLOW)
        collection.render()
    }

    @Test
    fun testDFSRandom() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 20
        val height = 20
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()
        val prunedGraphProvider = RandomPruneGraphProvider(graph, 0.2)
        val pruned = prunedGraphProvider.build()

        val collection = GraphCollection()
        collection.pointRadius = 10
        collection.lineStroke = 1.0f
        collection.addGraph(pruned, Color.BLUE)

        val vertices = pruned.storage.vertexStorage.toList()
        val minVertex = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val maxVertex = vertices.minByOrNull {  vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(minVertex != null)
        minVertex!!
        assert(maxVertex != null)
        maxVertex!!

        val algorithm = DFS(minVertex, maxVertex, pruned)
        val path = algorithm.evaluate()
        collection.addPoint(minVertex, color = Color.RED)
        collection.addPoint(maxVertex, color = Color.RED)
        collection.addPath(path, Color.RED)
        collection.addPoint(minVertex, color = Color.YELLOW)
        collection.render()
    }


}