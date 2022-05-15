package com.resnik.math.graph.maze.generator

import com.resnik.math.graph.maze.objects.provider.MazeProvider
import com.resnik.math.graph.maze.params.MazeParameterInterface
import com.resnik.math.graph.maze.params.SelfDescribedParameters

abstract class MazeGenerator(params: MazeParameterInterface) : SelfDescribedParameters(params), MazeProvider