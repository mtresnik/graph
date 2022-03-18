package com.resnik.math.graph.maze.ui

import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

fun BufferedImage.scale(scale : Double) : BufferedImage {
    val expectedWidth = (scale * this.width).toInt()
    val expectedHeight = (scale * this.height).toInt()
    val destination = BufferedImage(expectedWidth, expectedHeight, this.type)
    val graphics = destination.createGraphics()
    val transform = AffineTransform.getScaleInstance(scale, scale)
    graphics.drawRenderedImage(this, transform)
    return destination
}