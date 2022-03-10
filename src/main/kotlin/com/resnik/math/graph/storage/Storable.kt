package com.resnik.math.graph.storage

import java.io.InputStream
import java.io.OutputStream

interface Storable {

    fun writeTo(outputStream: OutputStream, close : Boolean = true)

    fun readFrom(inputStream: InputStream, close : Boolean = true)

}