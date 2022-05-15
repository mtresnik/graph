package com.resnik.math.graph.objects

import com.resnik.math.graph.storage.objects.graph.GraphStorage
import com.resnik.math.linear.array.ArrayPoint

open class Graph(var id: Long? = null, val storage: GraphStorage = GraphStorage()) : Cloneable {

    constructor(vararg points: ArrayPoint, id: Long? = null) : this(id = id) {
        this.storage.vertexStorage.saveAll(points.map { Vertex(*it.values) }.toSet())
    }

    constructor(vertices: Set<Vertex>, edges: Set<Edge>, id: Long? = null) : this(id = id) {
        this.storage.vertexStorage.saveAll(vertices)
        this.storage.edgeStorage.saveAll(edges)
    }

    public override fun clone(): Graph {
        return Graph(id = id, storage = storage.clone())
    }

}