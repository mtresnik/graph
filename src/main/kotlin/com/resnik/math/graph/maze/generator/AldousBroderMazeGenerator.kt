package com.resnik.math.graph.maze.generator

import com.resnik.math.graph.maze.objects.Maze
import com.resnik.math.graph.maze.objects.MazeBorder
import com.resnik.math.graph.maze.params.MazeCoordinate
import com.resnik.math.graph.maze.params.MazeParameterInterface

class AldousBroderMazeGenerator(params: MazeParameterInterface) : MazeGenerator(params) {

    override fun build(): Maze {
        val retMaze = Maze(params)
        val visited = mutableSetOf<MazeCoordinate>()
        val allCells = retMaze.cells.flatten().shuffled()
        val startCell = allCells.random()
        val numCells = allCells.size
        visited.add(startCell)
        var currentCell: MazeCoordinate = startCell
        while (numCells != visited.size) {
            var neighborConnections = retMaze.getConnections(currentCell)
                .filter { connection -> connection.to !in visited }
            if (neighborConnections.isEmpty()) {
                // Try to find connecting path from other visited first...
                neighborConnections = visited
                    .flatMap { mazeCoordinate -> retMaze.getConnections(mazeCoordinate) }
                    .toMutableSet()
                    .filter { mazeConnection -> mazeConnection.to !in visited }

                // If still empty, use last resort of picking a new start
                if (neighborConnections.isEmpty()) {
                    currentCell = allCells.firstOrNull { newCell -> newCell !in visited } ?: return retMaze
                    visited.add(currentCell)
                    continue
                }
            }
            val currConnection = neighborConnections.random()
            retMaze.setWall(currConnection.from, currConnection.direction, MazeBorder.PASSAGE)
            visited.add(currConnection.to)
            currentCell = currConnection.to
        }
        return retMaze
    }

}