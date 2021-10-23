package io.aklinker1.files.common.models

data class FileListHeader(val path: String, val displayName: String) {
  /**
   * For `FixateAdapter.getItemId`
   */
  override fun hashCode(): Int {
    return "FileListHeader-${path}".hashCode()
  }
}
