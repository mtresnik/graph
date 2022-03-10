package com.resnik.math.graph.algorithms.cost

import java.lang.IllegalStateException

class VertexWrapperComparator<T : VertexWrapper<T>>(val getCostMetric : (vertexWrapper : T) -> CostMetric) : Comparator<T> {

    override fun compare(o1: T?, o2: T?): Int {
        if(o1 == null && o2 == null) return 0
        if(o1 != null && o2 != null) {
            return CostMetricTotalComparator.compare(getCostMetric(o1), getCostMetric(o2))
        }
        throw IllegalStateException("One of o1/o2 is null o1=$o1 \t o2=$o2")
    }

}