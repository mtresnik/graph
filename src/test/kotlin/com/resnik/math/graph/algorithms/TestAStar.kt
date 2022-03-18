package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.provider.BoundedGraphProvider
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import com.resnik.math.linear.array.geometry.BoundingBox
import com.resnik.math.stats.stddev
import org.junit.jupiter.api.Test
import java.awt.Color

class TestAStar {

    @Test
    fun testAStar1() {
        val bbox = BoundingBox(ArrayPoint(0.0, 0.0), ArrayPoint(10.0, 10.0))
        val width = 100
        val height = 100
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
        val visitedListener = VisitRecorder()
        algorithm.addListener(visitedListener)
        val path = algorithm.evaluate()
        // collection.addPoints(visitedListener.toList(), Color.GREEN)
        collection.addPoint(minVertex, color = Color.RED)
        collection.addPoint(maxVertex, color = Color.RED)
        collection.addPath(path, Color.RED)

        collection.render()
    }

    @Test
    fun testAStarRandom() {
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

        val algorithm = AStar(GAParams(start, dest))
        val visitedListener = VisitRecorder()
        algorithm.addListener(visitedListener)
        val path = algorithm.evaluate()
        collection.addPoints(visitedListener.toList(), Color.GREEN)
        collection.addPoint(start, color = Color.RED)
        collection.addPoint(dest, color = Color.RED)
        collection.addPath(path, Color.RED)
        collection.addPoint(start, color = Color.YELLOW)
        collection.render()
    }

    @Test
    fun profileAStar() {
        val numIterations = 100
        val timeTakenSeconds =              mutableListOf<Double>()
        val bLineDistances =                mutableListOf<Double>()
        val actualDistances =               mutableListOf<Double>()
        val percentVisitation =             mutableListOf<Double>()
        val percentDifferenceFromBLine =    mutableListOf<Double>()

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

        println("Average Time Taken: ${timeTakenSeconds.average()} stddev: ${timeTakenSeconds.toDoubleArray().stddev()}")
        println("B Line Distance: ${bLineDistances.average()} stddev: ${bLineDistances.toDoubleArray().stddev()}")
        println("Actual Distance: ${actualDistances.average()} stddev: ${actualDistances.toDoubleArray().stddev()}")
        println("Percent Visitation: ${percentVisitation.average()} stddev: ${percentVisitation.toDoubleArray().stddev()}")
        println("Percent Difference From B Line: ${percentDifferenceFromBLine.average()} stddev: ${percentDifferenceFromBLine.toDoubleArray().stddev()}")


    }


}