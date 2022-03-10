package com.resnik.math.graph.algorithms.cost

import com.resnik.math.graph.objects.Vertex

class DefaultVertexWrapper(inner: Vertex, defaultCost : Double = Double.MAX_VALUE) : VertexWrapper<DefaultVertexWrapper>(inner, defaultCost) {

    override fun compareTo(other: DefaultVertexWrapper): Int {
        return CostMetricTotalComparator.compare(this.state.cost, other.state.cost)
    }

}