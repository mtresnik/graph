package com.resnik.math.graph.algorithms.cost

import com.resnik.math.graph.objects.PolyEdge


/*
* fixed (constant rate)
* variable (based on inputs)
* relative (based on other costs)
*
* unit optimization for cost functions
*
* build a symbolic cost function or some symbolic adapter to cost functions
*
* have a function wrapper for cost functions
*
* have some AI wrapper for cost functions
*
* */
interface CostFunction<W : VertexWrapper<W>> {

    fun straightLineCost(current : W, dest : W) : CostState

    fun polyEdgeCost(startCost : CostState, polyEdge : PolyEdge) : CostState

}