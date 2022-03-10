package com.resnik.math.graph.algorithms.cost

class CostMetric(var current : Double = 0.0, var total : Double = 0.0) {

    companion object {

        val TOTAL_COMPARATOR = CostMetricTotalComparator
        val CURRENT_COMPARATOR = CostMetricCurrentComparator

    }

}