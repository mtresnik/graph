package com.resnik.math.graph.hull

import com.resnik.math.graph.objects.Path
import com.resnik.math.graph.ui.GraphCollection
import com.resnik.math.linear.array.ArrayPoint
import org.junit.Ignore
import org.junit.Test

class TestConvex {

    fun randomVertices(max: Int = 20): List<ArrayPoint> {
        return Array(max) { ArrayPoint(Math.random(), Math.random()) }.toList()
    }

    @Test
    @Ignore
    fun testConvex() {
        val points = randomVertices()
        val hull = ConvexHull()
        val surround = hull.convexHull(points)
        val path = Path.fromPoints(surround)
        path.wrap()
        val collection = GraphCollection("Test Convex")
        collection.addPoints(points)
        collection.addPath(path)
        collection.render()
    }

}