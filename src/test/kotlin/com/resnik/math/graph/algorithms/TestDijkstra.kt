package com.resnik.math.graph.algorithms

import com.resnik.math.graph.objects.Edge
import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.Vertex
import com.resnik.math.graph.road.Intersection
import org.junit.jupiter.api.Test

class TestDijkstra : TestGraphRenderer() {

    @Test
    fun testDijkstra(){
        val vertices = mutableListOf(
            Vertex(0.0,0.0, id=1),
            Vertex(0.0,1.0, id=2),
            Vertex(2.0,3.0, id=3),
            Vertex(-1.0,0.0, id=4),
            Vertex(-5.0,10.0, id=5),
            Vertex(-5.0,-10.0, id=6),
            Vertex(-1.0,1.0, id=7)
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
        val dijkstra = Dijkstra(GAParams(start, dest))
        val visitedListener = VisitRecorder()
        dijkstra.addListener(visitedListener)
        val path = dijkstra.evaluate()
        renderIfSet(graph, start, dest, path, visitedListener)
    }

    @Test
    fun testIntersections(){
        val vertices = mutableListOf(
            Intersection(34.001, -73.0),
            Intersection(34.02, -73.01),
            Intersection(34.05, -73.02),
            Intersection(33.99, -73.01),
            Intersection(33.89, -73.05),
            Intersection(33.99, -72.99)
        )
        val roads = mutableSetOf<Edge>()
        roads.addAll(vertices[0].connectMultiple(vertices[1], vertices[2], vertices[3]))
        roads.addAll(vertices[1].connectMultiple(vertices[2], vertices[3], vertices[4]))
        roads.addAll(vertices[2].connectMultiple(vertices[3], vertices[4], vertices[5]))
        roads.addAll(vertices[3].connectMultiple(vertices[2], vertices[5], vertices[2]))
        roads.addAll(vertices[4].connectMultiple(vertices[1], vertices[5], vertices[3]))
        roads.addAll(vertices[5].connectMultiple(vertices[2], vertices[4], vertices[1]))
        val graph = Graph(vertices.toSet(), roads)
        val (start, dest) = vertices[0] to vertices[5]
        val dijkstra = Dijkstra(GAParams(start, dest))
        val visitedListener = VisitRecorder()
        dijkstra.addListener(visitedListener)
        val path = dijkstra.evaluate()
        renderIfSet(graph, start, dest, path, visitedListener)
    }

}