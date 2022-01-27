package com.resnik.math.graph.hull

import com.resnik.math.graph.Path
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import org.junit.jupiter.api.Test

class TestConvex {

    fun randomVertices(max:Int = 20): List<ArrayPoint> {
        return Array(max) { ArrayPoint(Math.random(), Math.random()) }.toList()
    }

    @Test
    fun testConvex(){
        val points = randomVertices()
        val hull = ConvexHull()
        val surround = hull.convexHull(points)
        val path = Path.fromPoints(surround)
        path.wrap()
        val collection: GraphCollection = GraphCollection("Test Convex")
        collection.addPoints(points)
        collection.addPath(path)
        collection.render()
    }

}