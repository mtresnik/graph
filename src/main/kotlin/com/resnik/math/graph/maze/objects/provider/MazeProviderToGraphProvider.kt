package com.resnik.math.graph.maze.objects.provider

import com.resnik.math.graph.objects.Graph
import com.resnik.math.graph.objects.provider.GraphProvider

class MazeProviderToGraphProvider(private val mazeProvider: MazeProvider) : GraphProvider {

    override fun build(): Graph {
        val maze = mazeProvider.build()
        val mazeToGraphProvider = MazeToGraphProvider(maze)
        return mazeToGraphProvider.build()
    }

}