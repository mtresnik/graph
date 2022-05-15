package com.resnik.math.graph.storage

import com.resnik.math.graph.Flaggable

object FlaggableSerializer {

    fun flagsFromString(inputs: List<String>, startIndex: Int, endIndex: Int): Collection<Long> {
        val flags = mutableSetOf<Long>()
        (startIndex..endIndex).forEach { index ->
            if (index >= 0 && index < inputs.size) {
                val currFlag = inputs[index].toLongOrNull()
                if (currFlag != null) flags.add(currFlag)
            }
        }
        return flags
    }

    fun flagsToString(flaggable: Flaggable): String {
        var retString = ""
        val flags = flaggable.getFlags()
        if (flags.isEmpty()) return retString
        retString += GraphConstants.Keys.SPACE
        retString += GraphConstants.Keys.END
        retString += GraphConstants.Keys.SPACE
        // add flags
        retString += flags.joinToString(GraphConstants.Keys.SPACE)
        return retString
    }

}