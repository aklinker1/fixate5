package io.aklinker1.files.common.utils.fs

import android.content.res.Resources
import io.aklinker1.files.R
import io.aklinker1.files.common.enums.GroupBy
import io.aklinker1.files.common.enums.SortBy
import io.aklinker1.files.common.models.*
import io.aklinker1.livefs.LiveFile

object FileSorter {
  /**
   * This adds the list header and section headers (when specified), as well as sorts the files by
   * their file names
   */
  fun loadExplorerList(
    resources: Resources,
    parent: LiveFile,
    files: List<LiveFile>,
    options: FileListOptions,
    listeners: FileListItemClickListeners?,
  ): List<Any> {
    if (!parent.file.exists()) {
      return listOf(DeletedFolder(parent.path, parent.nameOrAlias(resources)))
    }
    if (files.isEmpty()) {
      return listOf(NoFiles(parent.path, parent.nameOrAlias(resources)))
    }
    return when (options.groupBy) {
      GroupBy.NONE -> {
        val sorted = sort(files, options)
        val list = arrayListOf<Any>(FileListHeader(parent.path, parent.nameOrAlias(resources)))
        list.addAll(sorted.map {
          FolderListItem(
            displayName = it.nameOrAlias(resources),
            folder = it,
            multiselectMode = false,
            isSelected = false,
            onClick = listeners?.onFolderListItemClick
              ?: TODO("You must pass a click listener for FolderListItems"),
          )
        })
        list
      }
      GroupBy.TYPE -> {
        // Optimize?
        val filteredFolders = files.filter { it.file.isDirectory }
        val filteredFiles = files.filter { it.file.isFile }
        val list = arrayListOf<Any>(FileListHeader(parent.path, parent.nameOrAlias(resources)))

        if (filteredFolders.isNotEmpty()) {
          val sortedFolders = sort(filteredFolders.filter { it.file.isDirectory }, options)
          list.add(FileListSection(resources.getString(R.string.folders_section)))
          list.addAll(sortedFolders.map {
            FolderListItem(
              displayName = it.nameOrAlias(resources),
              folder = it,
              multiselectMode = false,
              isSelected = false,
              onClick = listeners?.onFolderListItemClick
                ?: TODO("You must pass a click listener for FolderListItems"),
            )
          })
        }

        if (filteredFiles.isNotEmpty()) {
          val sortedFiles = sort(filteredFiles.filter { it.file.isFile }, options)
          list.add(FileListSection(resources.getString(R.string.files_section)))
          list.addAll(sortedFiles.map {
            FolderListItem(
              displayName = it.nameOrAlias(resources),
              folder = it,
              multiselectMode = false,
              isSelected = false,
              onClick = listeners?.onFolderListItemClick
                ?: TODO("You must pass a click listener for FolderListItems"),
            )
          })
        }

        list
      }
    }
  }

  private fun sort(files: List<LiveFile>, options: FileListOptions): List<LiveFile> =
    when (options.sortBy) {
      SortBy.FILENAME_ASC -> files.sortedBy { it.name() }
      SortBy.FILENAME_DESC -> files.sortedByDescending { it.name() }
      SortBy.LAST_MODIFIED_ASC -> files.sortedBy { it.file.lastModified() }
      SortBy.LAST_MODIFIED_DESC -> files.sortedByDescending { it.file.lastModified() }
    }
}
