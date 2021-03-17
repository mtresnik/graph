package com.resnik.math.graph.hull

import com.resnik.math.Point
import com.resnik.math.graph.Path
import com.resnik.math.graph.ui.GraphCollection
import org.junit.jupiter.api.Test

class TestConvex {

    fun randomVertices(max:Int = 20): List<Point> {
        val points: Array<Point> = Array(max) { Point() }
        for(i in 0 until max){
            points[i] = Point(Math.random(), Math.random())
        }
        return points.toList()
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