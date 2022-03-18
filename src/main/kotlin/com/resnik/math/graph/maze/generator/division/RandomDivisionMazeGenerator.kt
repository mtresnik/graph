package com.resnik.math.graph.maze.generator.division

import com.resnik.math.graph.maze.params.MazeParameterInterface

class RandomDivisionMazeGenerator(params: MazeParameterInterface, numDivide : Int = 1) : DivisionMazeGenerator(params, numDivide) {

    override fun indexGenerator(length: Double, min: Double): Double {
        val max = min+length
        return (max - min) * Math.random() + min
    }
}