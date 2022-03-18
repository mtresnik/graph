package com.resnik.math.graph.maze.generator.division

import com.resnik.math.linear.array.geometry.BoundingBox


fun BoundingBox.width(): Double {
    return this.maxX() - this.minX()
}

fun BoundingBox.height(): Double {
    return this.maxY() - this.minY()
}