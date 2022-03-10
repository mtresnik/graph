package com.resnik.math.graph.storage.file

import com.resnik.math.graph.storage.Storable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

interface FileStorable : Storable {

    fun getExtension() : String

    fun getName() : String

    private fun getExpectedFile(parent: File) : File = File(parent, getName() + "." + getExtension())

    private fun getNextAvailableFile(parent : File) : File {
        val fileName = getName()
        var currName = fileName + "." + getExtension()
        var childFile = File(parent, currName)
        var index = 1
        while(childFile.exists()) {
            currName = fileName + index + "." + getExtension()
            childFile = File(parent, currName)
            index++
        }
        return childFile
    }

    fun canRead(file : File) : Boolean = file.absolutePath.endsWith("." + getExtension())

    fun overwrite() : Boolean = true

    fun hasContent() : Boolean = true

    fun getFile(parent: File) : File {
        if(overwrite()) return getExpectedFile(parent)
        return getNextAvailableFile(parent)
    }

    fun saveFromParent(parent : File) {
        val child = getFile(parent)
        saveTo(child)
    }

    fun saveTo(file: File) {
        val fileOutputStream = FileOutputStream(file)
        writeTo(fileOutputStream, true)
        fileOutputStream.close()
    }

    fun loadFromParent(parent: File) {
        loadFrom(getFile(parent))
    }

    fun loadFrom(file : File) {
        if(canRead(file)) {
            readFrom(FileInputStream(file))
        }
    }

}