package io.aklinker1.files.common.models

import io.aklinker1.livefs.LiveFile

typealias OnFileListItemClick = (file: LiveFile) -> Unit
typealias OnFolderListItemClick = (file: LiveFile) -> Unit
typealias OnNavigationPathClick = (index: Int) -> Unit

class FileListItemClickListeners(
  val onFileListItemClick: OnFileListItemClick? = null,
  val onFolderListItemClick: OnFolderListItemClick? = null,
  val onNavigationPathClick: OnNavigationPathClick? = null,
)
