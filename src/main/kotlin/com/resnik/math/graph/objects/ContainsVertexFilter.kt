package com.resnik.math.graph.objects

import com.resnik.math.linear.array.geometry.Shape2d

class ContainsVertexFilter(private val region: Shape2d<*>) : VertexFilter {

    override fun isValid(type: Vertex): Boolean = region.contains(type)

}