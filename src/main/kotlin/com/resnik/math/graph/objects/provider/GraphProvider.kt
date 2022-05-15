package com.resnik.math.graph.objects.provider

import com.resnik.math.graph.objects.Graph

interface GraphProvider {

    fun build(): Graph

}