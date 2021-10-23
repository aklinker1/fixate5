package io.aklinker1.livefs

import java.io.File

class LiveFile(val file: File) {

    constructor(path: String) : this(File(path))

    constructor(parent: String, filename: String) : this(File(parent, filename))

    constructor(parentFile: File, filename: String) : this(parentFile.absolutePath, filename)

    val path: String
        get() = file.absolutePath

    private fun parentPath(): String = if (path == "/") "/" else path.substring(0, path.lastIndexOf('/'))

    fun parent() = LiveFile(parentPath())

    fun name(): String = if (path == "/") "/" else path.substring(path.lastIndexOf("/") + 1)

    private fun extensionWithDotOffset(charOffset: Int): String {
        val filename = name()
        if (filename.contains('.')) {
            return filename.substring(filename.lastIndexOf('.') + charOffset)
        }
        return ""
    }

    fun extensionWithDot(): String = extensionWithDotOffset(0)

    fun extensionNoDot(): String = extensionWithDotOffset(1)

    fun children(): List<LiveFile> = file.listFiles()?.map(::LiveFile) ?: emptyList()

    fun liveChildren() = FileChildrenLiveData(this)

    override fun toString(): String {
        return "LiveFile('$path')"
    }

}
