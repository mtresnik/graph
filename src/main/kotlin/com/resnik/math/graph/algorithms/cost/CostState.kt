package com.resnik.math.graph.algorithms.cost

open class CostState(defaultCost: Double = 0.0) {

    var cost = CostMetric(total = defaultCost)

    companion object {
        val COST_COMPARATOR = Comparator<CostState> { o1, o2 -> CostMetric.TOTAL_COMPARATOR.compare(o1.cost, o2.cost) }
    }

}