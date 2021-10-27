package io.aklinker1.files.common.utils.fs

import android.webkit.MimeTypeMap
import io.aklinker1.livefs.LiveFile

class MimeTypeMapPlus {
  private val map: HashMap<String, String> = HashMap()
  private val originalMapping: MimeTypeMap = MimeTypeMap.getSingleton()

  init {
    map["md"] = "text/markdown"
  }

  operator fun get(file: LiveFile): String? {
    val extension = file.extensionNoDot()
    var mimeType = originalMapping.getMimeTypeFromExtension(extension)
    if (mimeType == null) mimeType = map[extension]
    return mimeType
  }

}
