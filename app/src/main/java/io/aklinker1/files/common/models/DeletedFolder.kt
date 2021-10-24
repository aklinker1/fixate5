package io.aklinker1.files.common.models

data class DeletedFolder(val path: String, val displayName: String) {
  /**
   * For `FixateAdapter.getItemId`
   */
  override fun hashCode(): Int {
    return "DeletedFolder".hashCode()
  }
}
