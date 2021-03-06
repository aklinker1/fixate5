package io.aklinker1.files.common.adapters.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.aklinker1.files.common.adapters.view_holders.*
import io.aklinker1.files.common.models.*

class FixateAdapter(var items: List<Any> = listOf()) :
  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  companion object {
    const val NAVIGATION_PATH_VIEW_TYPE = 0
    const val FILE_LIST_HEADER_VIEW_TYPE = 1
    const val FILE_LIST_SECTION_VIEW_TYPE = 2

    // const val FILE_LIST_ITEM_VIEW_TYPE = 3
    const val FOLDER_LIST_ITEM_VIEW_TYPE = 4

    const val NO_FILES_VIEW_TYPE = 5
    const val DELETED_FOLDER_VIEW_TYPE = 6
  }

  override fun getItemViewType(position: Int): Int {
    return when (items[position]) {
      is NavigationPath -> NAVIGATION_PATH_VIEW_TYPE
      is FileListHeader -> FILE_LIST_HEADER_VIEW_TYPE
      is FileListSection -> FILE_LIST_SECTION_VIEW_TYPE
      // is FileListItem -> FILE_LIST_ITEM_VIEW_TYPE
      is FolderListItem -> FOLDER_LIST_ITEM_VIEW_TYPE
      is NoFiles -> NO_FILES_VIEW_TYPE
      is DeletedFolder -> DELETED_FOLDER_VIEW_TYPE
      else -> TODO("Unsupported item type: ${items[position]}")
    }
  }

  override fun getItemId(position: Int): Long {
    return items[position].hashCode().toLong()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      NAVIGATION_PATH_VIEW_TYPE -> NavigationPathViewHolder(parent)
      FILE_LIST_HEADER_VIEW_TYPE -> FileListHeaderViewHolder(parent)
      FILE_LIST_SECTION_VIEW_TYPE -> FileListSectionViewHolder(parent)
      // FILE_LIST_ITEM_VIEW_TYPE -> FileListItemViewHolder(parent)
      FOLDER_LIST_ITEM_VIEW_TYPE -> FolderListItemViewHolder(parent)
      NO_FILES_VIEW_TYPE -> NoFilesViewHolder(parent)
      DELETED_FOLDER_VIEW_TYPE -> DeletedFolderViewHolder(parent)
      else -> TODO("Unknown viewType: $viewType")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is NavigationPathViewHolder -> holder.onBind(items[position] as NavigationPath)
      is FileListHeaderViewHolder -> holder.onBind(items[position] as FileListHeader)
      is FileListSectionViewHolder -> holder.onBind(items[position] as FileListSection)
      // is FileListItemViewHolder -> holder.onBind(items[position] as FileListItem)
      is FolderListItemViewHolder -> holder.onBind(items[position] as FolderListItem)
      is NoFilesViewHolder -> holder.onBind(items[position] as NoFiles)
      is DeletedFolderViewHolder -> holder.onBind(items[position] as DeletedFolder)
      else -> TODO("Unknown view holder: ${holder::class.simpleName}")
    }
  }

  override fun getItemCount(): Int = items.size
}
