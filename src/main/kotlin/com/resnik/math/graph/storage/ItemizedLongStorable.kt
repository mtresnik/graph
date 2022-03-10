package com.resnik.math.graph.storage

import com.resnik.math.graph.IDGenerator
import com.resnik.math.graph.Identifyable

abstract class ItemizedLongStorable<ITEM : Identifyable> : ItemizedStorable<Long, ITEM>() {

    private val idGenerator = IDGenerator()

    override fun save(value: ITEM) {
        val id = value.getID() ?: idGenerator.genId()
        // Ensure the id isn't null after being saved...
        value.setID(id)
        this[id] = value
    }

}