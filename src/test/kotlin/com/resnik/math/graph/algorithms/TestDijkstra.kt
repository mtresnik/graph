package com.resnik.math.graph.algorithms

import com.resnik.math.graph.Edge
import com.resnik.math.graph.Graph
import com.resnik.math.graph.Vertex
import com.resnik.math.graph.road.Intersection
import com.resnik.math.graph.ui.GraphCollection
import org.junit.jupiter.api.Test

class TestDijkstra {

    @Test
    fun testDijkstra(){
        val vertices = mutableListOf(
            Vertex(0.0,0.0),
            Vertex(0.0,1.0),
            Vertex(2.0,3.0),
            Vertex(-1.0,0.0),
            Vertex(-5.0,10.0),
            Vertex(-5.0,-10.0),
            Vertex(-1.0,1.0)
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
        val dijkstra = Dijkstra(vertices[0], vertices[5], graph)
        val path = dijkstra.evaluate()
        println(path)
        assert(path.getDistance() == 18.370374335697388)
        val collection: GraphCollection = GraphCollection("Literal Dijkstra")
        collection.addGraph(graph)
        collection.addPath(path)
        collection.render()
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
        val dijkstra = Dijkstra(vertices[0], vertices[5], graph)
        val path = dijkstra.evaluate()
        println(path)
        val collection: GraphCollection = GraphCollection("Location Dijkstra")
        collection.addGraph(graph)
        collection.addPath(path)
        collection.render()
    }

}