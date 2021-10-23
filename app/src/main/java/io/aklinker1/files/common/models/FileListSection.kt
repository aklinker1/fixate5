package io.aklinker1.files.common.models

data class FileListSection(val string: String) {
  /**
   * For `FixateAdapter.getItemId`
   */
  override fun hashCode(): Int {
    return "FileListSection-${string}".hashCode()
  }
}
