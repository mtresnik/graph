package com.resnik.math

open class Point(vararg val values: Double) {

    operator fun minus(other: Point) : Vector = Vector(*values.zip(other.values) { a, b -> a - b }.toDoubleArray())

    fun distanceTo(other: Point): Double = (this - other).magnitude()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (values.contentEquals((other as Point).values)) return true
        return false
    }

    override fun hashCode(): Int = values.contentHashCode()

    override fun toString(): String = "(${values.contentToString()})"

}

