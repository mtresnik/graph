package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.graph.objects.provider.BoundedGraphProvider
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import com.resnik.math.stats.stddev
import org.junit.Ignore
import org.junit.Test

class TestAStar : TestGraphRenderer() {

    @Test
    @Ignore
    fun testAStar() {
        val vertices = mutableListOf(
            Vertex(0.0, 0.0, id = 1),
            Vertex(0.0, 1.0, id = 2),
            Vertex(2.0, 3.0, id = 3),
            Vertex(-1.0, 0.0, id = 4),
            Vertex(-5.0, 10.0, id = 5),
            Vertex(-5.0, -10.0, id = 6),
            Vertex(-1.0, 1.0, id = 7)
        )
        val edges = mutableSetOf<Edge>()
        edges.addAll(vertices[0].connectMultiple(vertices[1], vertices[2], vertices[3]))
        edges.addAll(vertices[1].connectMultiple(vertices[2], vertices[3], vertices[4]))
        edges.addAll(vertices[2].connectMultiple(vertices[3], vertices[4], vertices[5]))
        edges.addAll(vertices[3].connectMultiple(vertices[1], vertices[2], vertices[6]))
        edges.addAll(vertices[4].connectMultiple(vertices[1], vertices[2], vertices[5]))
        edges.addAll(vertices[5].connectMultiple(vertices[2], vertices[4], vertices[3]))
        edges.addAll(vertices[6].connectMultiple(vertices[5], vertices[2], vertices[4]))
        val graph = Graph(vertices.toSet(), edges)
        val (start, dest) = vertices[0] to vertices[5]
        val astar = AStar(GAParams(start, dest))
        val visitedListener = VisitRecorder()
        astar.addListener(visitedListener)
        val path = astar.evaluate()
        renderIfSet(graph, start, dest, path, visitedListener)
    }


    @Test
    @Ignore
    fun testAStar1() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 100
        val height = 100
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()

        val vertices = graph.storage.vertexStorage.toList()
        val start = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.minX(), bbox.minY())) }
        val dest = vertices.minByOrNull { vertex -> vertex.distanceTo(ArrayPoint(bbox.maxX(), bbox.maxY())) }
        assert(start != null)
        assert(dest != null)

        val algorithm = AStar(GAParams(start!!, dest!!))
        val visitedListener = VisitRecorder()
        algorithm.addListener(visitedListener)
        val path = algorithm.evaluate()
        renderIfSet(graph, start, dest, path, visitedListener)
    }

    @Test
    @Ignore
    fun testAStarRandom() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 20
        val height = 20
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        val graph = boundedGraphProvider.build()


        val vertices = graph.storage.vertexStorage.toList()
        val start = vertices.random()
        val dest = vertices.random()

        val algorithm = AStar(GAParams(start, dest))
        val visitedListener = VisitRecorder()
        algorithm.addListener(visitedListener)
        val path = algorithm.evaluate()
        renderIfSet(graph, start, dest, path, visitedListener)
    }


    @Test
    @Ignore
    fun profileAStar() {
        val numIterations = 100
        val timeTakenSeconds = mutableListOf<Double>()
        val bLineDistances = mutableListOf<Double>()
        val actualDistances = mutableListOf<Double>()
        val percentVisitation = mutableListOf<Double>()
        val percentDifferenceFromBLine = mutableListOf<Double>()

        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 200
        val height = 200
        val boundedGraphProvider = BoundedGraphProvider(bbox, width, height)
        repeat(numIterations) {
            val graph = boundedGraphProvider.build()
            val start = System.currentTimeMillis()
            val vertices = graph.storage.vertexStorage.toList()
            val origin = vertices.random()
            val destination = vertices.random()
            val algorithm = AStar(GAParams(origin, destination))
            val path = algorithm.evaluate()
            val dt = (System.currentTimeMillis() - start) / 1000.0
            timeTakenSeconds.add(dt)
            val bLine = origin.distanceTo(destination)
            val actual = path.getDistance()
            bLineDistances.add(bLine)
            actualDistances.add(actual)
            val percentVisited = algorithm.numVisited().toDouble() / vertices.size
            percentVisitation.add(percentVisited)
            percentDifferenceFromBLine.add(actual / bLine - 1.0)
        }

        println(
            "Average Time Taken: ${timeTakenSeconds.average()} stddev: ${
                timeTakenSeconds.toDoubleArray().stddev()
            }"
        )
        println("B Line Distance: ${bLineDistances.average()} stddev: ${bLineDistances.toDoubleArray().stddev()}")
        println("Actual Distance: ${actualDistances.average()} stddev: ${actualDistances.toDoubleArray().stddev()}")
        println(
            "Percent Visitation: ${percentVisitation.average()} stddev: ${
                percentVisitation.toDoubleArray().stddev()
            }"
        )
        println(
            "Percent Difference From B Line: ${percentDifferenceFromBLine.average()} stddev: ${
                percentDifferenceFromBLine.toDoubleArray().stddev()
            }"
        )
    }


}