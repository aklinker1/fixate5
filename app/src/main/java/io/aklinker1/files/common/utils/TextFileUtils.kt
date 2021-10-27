package io.aklinker1.files.common.utils

import java.io.File
import java.io.InputStream

object TextFileUtils {
  fun readFile(file: File): String {
    val text = StringBuilder()
    file.forEachLine {
      text.append(it).append('\n')
    }
    return text.toString()
  }
}
