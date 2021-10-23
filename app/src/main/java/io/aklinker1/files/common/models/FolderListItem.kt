package io.aklinker1.files.common.models

import io.aklinker1.livefs.LiveFile

data class FolderListItem(
  val displayName: String,
  val folder: LiveFile,
  val multiselectMode: Boolean,
  val isSelected: Boolean,
  val onClick: OnFolderListItemClick,
) {
  /**
   * For `FixateAdapter.getItemId`
   */
  override fun hashCode(): Int {
    return "FolderListItem-${folder.path}".hashCode()
  }
}
