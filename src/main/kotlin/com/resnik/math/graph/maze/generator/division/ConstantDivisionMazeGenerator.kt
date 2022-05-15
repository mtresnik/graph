package com.resnik.math.graph.maze.generator.division

import com.resnik.math.graph.maze.params.MazeParameterInterface

class ConstantDivisionMazeGenerator(params: MazeParameterInterface, val ratio: Double, numDivide: Int = 1) :
    DivisionMazeGenerator(params, numDivide) {

    override fun indexGenerator(length: Double, min: Double): Double {
        val max = length + min
        return (length * ratio + min).coerceIn(min, max)
    }

}