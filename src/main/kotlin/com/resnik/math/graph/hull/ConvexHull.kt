package com.resnik.math.graph.hull

import com.resnik.math.Point
import java.util.*

class ConvexHull {

    fun convexHull(points: List<Point>) : Collection<Point>{
        Collections.sort(points)
        val hull = mutableListOf<Point>()
        points.forEach {
            while(hull.size >= 2 && !isCounterClockwise(hull[hull.lastIndex - 1], hull.last(), it)){
                hull.removeAt(hull.lastIndex)
            }
            hull.add(it)
        }
        val minSize = hull.size + 1
        for(i in points.lastIndex - 1 downTo 0){
            val point = points[i]
            while(hull.size >= minSize && !isCounterClockwise(hull[hull.lastIndex - 1], hull.last(), point)){
                hull.removeAt(hull.lastIndex)
            }
            hull.add(point)
        }
        hull.removeAt(hull.lastIndex)
        return hull
    }

    // Well established CCW reference
    private fun isCounterClockwise(a: Point, b: Point, c: Point) = ((b.x() - a.x()) * (c.y() - a.y())) > ((b.y() - a.y()) * (c.x() - a.x()))


}