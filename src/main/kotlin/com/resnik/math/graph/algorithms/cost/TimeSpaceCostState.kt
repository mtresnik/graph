package com.resnik.math.graph.algorithms.cost

class TimeSpaceCostState : CostState() {

    var time = CostMetric()
    val distance = CostMetric()

    companion object {
        val TIME_COMPARATOR =
            Comparator<TimeSpaceCostState> { o1, o2 -> CostMetric.TOTAL_COMPARATOR.compare(o1.time, o2.time) }
        val DISTANCE_COMPARATOR =
            Comparator<TimeSpaceCostState> { o1, o2 -> CostMetric.TOTAL_COMPARATOR.compare(o1.distance, o2.distance) }
    }

}