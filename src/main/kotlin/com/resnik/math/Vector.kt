package com.resnik.math

import kotlin.math.pow

class Vector(private vararg val values: Double) {

    operator fun plus(other: Vector): Vector = Vector(*values.zip(other.values) { a, b -> a + b }.toDoubleArray())

    operator fun minus(other: Vector): Vector = Vector(*values.zip(other.values) { a, b -> a - b }.toDoubleArray())

    operator fun times(other: Vector): Double = values.zip(other.values) {a, b -> a * b}.sum()

    operator fun get(index: Int) : Double = values[index]

    fun magnitude(): Double = values.map { v -> v.pow(2) }.sum().pow(0.5)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vector

        if (!values.contentEquals(other.values)) return false

        return true
    }

    override fun hashCode(): Int = values.contentHashCode()

    override fun toString(): String = "<${values.contentToString()}>"

}