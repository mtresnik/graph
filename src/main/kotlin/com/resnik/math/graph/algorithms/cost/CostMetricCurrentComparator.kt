package com.resnik.math.graph.algorithms.cost

object CostMetricCurrentComparator : Comparator<CostMetric> {
    override fun compare(o1: CostMetric?, o2: CostMetric?): Int {
        if (o1 == null && o2 == null) return 0
        if (o1 != null && o2 != null) {
            return o1.current.compareTo(o2.current)
        }
        throw IllegalStateException("One of o1/o2 is null o1=$o1 \t o2=$o2")
    }
}