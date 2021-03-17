package com.resnik.math

import com.resnik.math.graph.Edge
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

open class Point(vararg val values: Double) : Comparable<Point> {

    fun x(): Double = this[0]

    fun y(): Double = this[1]

    fun z(): Double = this[2]

    fun w(): Double = this[3]

    operator fun minus(other: Point) : Vector = Vector(*values.zip(other.values) { a, b -> a - b }.toDoubleArray())

    open fun distanceTo(other: Point): Double = (this - other).magnitude()

    fun distanceTo(p1: Point, p2: Point): Double {
        return abs((p2.x() - p1.x()) * (p1.y() - this.y()) - (p1.x() - this.x()) * (p2.y() - p1.y())) /
                sqrt((p2.x() - p1.x()).pow(2.0) + (p2.y() - p1.y()).pow(2.0))
    }

    fun leftOf(p1: Point, p2: Point): Boolean {
        return compareTo(p1, p2) == +1
    }

    fun cross(p1: Point, p2: Point) : Double {
        val thisX = this.x()
        val thisY = this.y()
        val x1 = p1.x()
        val y1 = p1.y()
        val x2 = p2.x()
        var y2 = p2.y()
        return (x2 - x1) * (thisY - y1) - (y2 - y1) * (thisX - x1)
    }

    override fun compareTo(other: Point): Int {
        values.forEachIndexed{ index, value ->
            if(value != other.values[index]){
                return value.compareTo(other.values[index])
            }
        }
        return 0
    }

    fun compareTo(edge: Edge) : Int {
        return compareTo(edge.from, edge.to)
    }

    fun compareTo(p1: Point, p2: Point) : Int {
        val res = cross(p1, p2)
        if(res < 0){
            return -1
        }else if(res > 0){
            return +1
        }
        return 0
    }
    operator fun get(index: Int) : Double = values[index]

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (values.contentEquals((other as Point).values)) return true
        return false
    }

    override fun hashCode(): Int = values.contentHashCode()

    override fun toString(): String = "(${values.contentToString()})"

}

