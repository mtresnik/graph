package com.resnik.math.graph.road

import com.resnik.math.graph.objects.PolyEdge
import kotlin.properties.Delegates

// Speed is in mps
class Road : PolyEdge {

    var name : String
    var speedLimit by Delegates.notNull<Double>()

    constructor(geometry: List<Intersection>, name: String = "Unnamed Road", speedLimit: Double = 15.0)
            : super(geometry, {a,b -> RoadSegment(a as Intersection,b as Intersection)}){
        this.name = name
        this.speedLimit = speedLimit
    }

    constructor(vararg intersections: Intersection) : this(intersections.toList()) { }

    fun getTime() : Double = getDistance() / speedLimit

    override fun toString(): String = "$name - $speedLimit m/s - ${super.toString()}"

}