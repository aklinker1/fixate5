package io.aklinker1.files.common.models

data class NoFiles(val path: String, val displayName: String) {
  /**
   * For `FixateAdapter.getItemId`
   */
  override fun hashCode(): Int {
    return 0
  }
}
