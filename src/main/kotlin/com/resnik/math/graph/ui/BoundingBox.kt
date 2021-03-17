package com.resnik.math.graph.ui

import com.resnik.math.Point
import java.lang.Double.max
import java.lang.Double.min
import java.lang.IllegalArgumentException
import kotlin.math.abs

class BoundingBox(var minX: Double, var minY: Double, var maxX: Double, var maxY: Double) {

    constructor() : this(Double.MAX_VALUE,Double.MAX_VALUE,-Double.MAX_VALUE,-Double.MAX_VALUE)

    operator fun contains(point: Point) : Boolean {
        if(point.values.size < 2){
            throw IllegalArgumentException("Must be >= 2 dim")
        }
        val x = point.values[0]
        val y = point.values[1]
        return x in minX..maxX && y in minY..maxY
    }

    fun update(point: Point){
        if(point.values.size < 2){
            throw IllegalArgumentException("Must be >= 2 dim")
        }
        val x = point.x()
        val y = point.y()
        minX = min(x, minX)
        minY = min(y, minY)
        maxX = max(x, maxX)
        maxY = max(y, maxY)
    }

    fun width() : Double = abs(maxX - minX)

    fun height() : Double = abs(maxY - minY)

    override fun toString(): String = "($minX,$minY) ($maxX,$maxY)"

}