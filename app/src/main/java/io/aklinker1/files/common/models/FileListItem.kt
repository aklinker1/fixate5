package io.aklinker1.files.common.models

import io.aklinker1.livefs.LiveFile

data class FileListItem(val displayName: String, val file: LiveFile) {
  /**
   * For `FixateAdapter.getItemId`
   */
  override fun hashCode(): Int {
    return "FileListItem-${file.path}".hashCode()
  }
}
