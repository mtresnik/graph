package com.resnik.math.graph.maze.objects.provider

import com.resnik.math.graph.maze.objects.Maze

interface MazeProvider {

    fun build() : Maze

}