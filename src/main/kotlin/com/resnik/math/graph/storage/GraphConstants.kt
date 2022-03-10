package com.resnik.math.graph.storage

object GraphConstants {

    object Common {

        const val SIZE = "size"
        const val BOUNDING_BOX = "bbox"

    }

    object Keys {

        val SPACE =         " "
        val VERTEX =        "v"
        val EDGE =          "e"
        val GRAPH =         "g"
        val POLYEDGE =      "p"
        val PATH =          "t"
        val END =           "|"

        val ROAD =          "r"
        val SEGMENT =       "s"
        val INTERSECTION =  "i"

        val HEADER =        "header"

        object Extensions {

            private const val GRAPH_BASE = "rg"
            val VERTEX =  "${GRAPH_BASE}${Keys.VERTEX}"
            val EDGE =    "${GRAPH_BASE}${Keys.EDGE}"
            val GRAPH =   "${GRAPH_BASE}${Keys.GRAPH}"
            val PATH =    "${GRAPH_BASE}${Keys.PATH}"

            private const val STREETMAP_EXTENSION =      "rsm"
            val ROAD =    "$STREETMAP_EXTENSION${Keys.ROAD}"
            val SEGMENT = "$STREETMAP_EXTENSION${Keys.ROAD}"
            val INTERSECTION = "$STREETMAP_EXTENSION${Keys.ROAD}"

        }

    }

    object Names {

        val VERTICES =      "vertices"
        val EDGES =         "edges"
        val PATHS =         "paths"
        val GRAPHS =        "graph"

    }


}