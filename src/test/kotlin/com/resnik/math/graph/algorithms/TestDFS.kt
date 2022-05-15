package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.provider.BoundedGraphProvider
import com.resnik.math.graph.objects.provider.RandomPruneGraphProvider
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import org.junit.jupiter.api.Test

class TestDFS : TestGraphRenderer() {

    @Test
    fun testDFS1() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 20
        val height = 20
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()

        val vertices = graph.storage.vertexStorage.toList()
        val start = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val dest = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(start != null)
        start!!
        assert(dest != null)
        dest!!

        val algorithm = DFS(GAParams(start, dest))
        val visitedListener = VisitRecorder()
        algorithm.addListener(visitedListener)
        val path = algorithm.evaluate()

        renderIfSet(graph, start, dest, path, visitedListener)
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

        val vertices = pruned.storage.vertexStorage.toList()
        val start = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val dest = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(start != null)
        start!!
        assert(dest != null)
        dest!!

        val algorithm = DFS(GAParams(start, dest))
        val visitedListener = VisitRecorder()
        algorithm.addListener(visitedListener)
        val path = algorithm.evaluate()
        renderIfSet(graph, start, dest, path)
    }


}