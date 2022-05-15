package com.resnik.math.graph.algorithms.cost

object CostMetricTotalComparator : Comparator<CostMetric> {
    override fun compare(o1: CostMetric?, o2: CostMetric?): Int {
        if (o1 == null && o2 == null) return 0
        if (o1 != null && o2 != null) {
            return o1.total.compareTo(o2.total)
        }
        throw IllegalStateException("One of o1/o2 is null o1=$o1 \t o2=$o2")
    }
}