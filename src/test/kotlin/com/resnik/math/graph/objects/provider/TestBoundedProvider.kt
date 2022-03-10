package com.resnik.math.graph.objects.provider

import com.resnik.math.graph.algorithms.AStar
import com.resnik.math.graph.algorithms.GAParams
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import org.junit.jupiter.api.Test
import java.awt.Color

class TestBoundedProvider {

    @Test
    fun testProvider1() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 20
        val height = 20
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()

        val collection = GraphCollection()
        collection.pointRadius = 10
        collection.addGraph(graph, color = Color.RED)
        collection.render()
    }

    @Test
    fun testProvider2() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 20
        val height = 20
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()

        val prunedGraphProvider = RandomPruneGraphProvider(graph, 0.1)
        val pruned = prunedGraphProvider.build()

        val collection = GraphCollection()
        collection.pointRadius = 10
        collection.lineStroke = 2.0f
        collection.addGraph(pruned, color = Color.BLUE)

        collection.render()
    }

    @Test
    fun testProvider3() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 30
        val height = 30
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()

        val prunedGraphProvider = RandomPruneGraphProvider(graph, 0.2)
        val pruned = prunedGraphProvider.build()

        val collection = GraphCollection()
        val g = 50
        collection.background = Color(g,g, g)
        collection.pointRadius = 10
        collection.lineStroke = 2.0f
        collection.addGraph(pruned, Color(0, 100, 250))

        val vertices = pruned.storage.vertexStorage.toList()
        val minVertex = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val maxVertex = vertices.minByOrNull {  vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(minVertex != null)
        assert(maxVertex != null)

        val algorithm = AStar(GAParams(minVertex!!, maxVertex!!))
        val path = algorithm.evaluate()
        println(path)

        val pathColor = Color(250, 100, 100)
        collection.addPoint(minVertex, color = pathColor)
        collection.addPoint(maxVertex, color = pathColor)
        collection.addPath(path, pathColor)

        collection.render()
    }

    @Test
    fun testProvider4() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 20
        val height = 20
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()

        val collection = GraphCollection()
        collection.pointRadius = 10
        collection.lineStroke = 2.0f
        collection.addGraph(graph, Color.BLUE)

        val vertices = graph.storage.vertexStorage.toList()
        val minVertex = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val maxVertex = vertices.minByOrNull {  vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(minVertex != null)
        assert(maxVertex != null)

        val algorithm = AStar(GAParams(minVertex!!, maxVertex!!))
        val path = algorithm.evaluate()
        println(path)

        collection.addPoint(minVertex, color = Color.RED)
        collection.addPoint(maxVertex, color = Color.RED)
        collection.addPath(path, Color.RED)

        collection.render()
    }

    @Test
    fun testProviderCloned() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 20
        val height = 20
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()

        val prunedGraphProvider = RandomPruneGraphProvider(graph, 0.2)
        val pruned = prunedGraphProvider.build()

        val collection = GraphCollection()
        val g = 50
        collection.background = Color(g,g, g)
        collection.pointRadius = 10
        collection.lineStroke = 2.0f
        val graphColor = Color(0, 100, 250)
        collection.addGraph(pruned, graphColor)

        val vertices = pruned.storage.vertexStorage.toList()
        val minVertex = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val maxVertex = vertices.minByOrNull {  vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(minVertex != null)
        assert(maxVertex != null)

        val algorithm = AStar(GAParams(minVertex!!, maxVertex!!))
        val path = algorithm.evaluate()
        println(path)

        val pathColor = Color(250, 100, 100)
        collection.addPoint(minVertex, color = pathColor)
        collection.addPoint(maxVertex, color = pathColor)
        collection.addPath(path, pathColor)

        collection.render()

        val pruned2 = prunedGraphProvider.build()
        val collection2 = GraphCollection()
        collection2.background = collection.background
        collection2.pointRadius = collection.pointRadius
        collection2.lineStroke = collection.lineStroke
        collection2.addGraph(pruned2, graphColor)

        collection2.render()

    }

}