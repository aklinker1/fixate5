package io.aklinker1.files.file_list

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import io.aklinker1.files.common.base.BaseViewModel
import io.aklinker1.files.common.enums.GroupBy
import io.aklinker1.files.common.enums.SortBy
import io.aklinker1.files.common.models.FileListItemClickListeners
import io.aklinker1.files.common.models.FileListOptions
import io.aklinker1.files.common.utils.fs.FileSorter
import io.aklinker1.livefs.LiveFile

class FileListViewModel @Deprecated("Use FileListViewModel.from() instead") constructor() :
  BaseViewModel() {

  companion object {
    fun from(owner: ViewModelStoreOwner) = ViewModelProvider(owner)[FileListViewModel::class.java]
  }

  private val items: MutableLiveData<List<Any>> by lazy {
    MutableLiveData(listOf())
  }

  private lateinit var parent: LiveFile
  private lateinit var resources: Resources
  private lateinit var listeners: FileListItemClickListeners
  private var listOptions = FileListOptions(GroupBy.TYPE, SortBy.FILENAME_ASC)

  fun init(resources: Resources, parent: LiveFile, listeners: FileListItemClickListeners) {
    this.resources = resources
    this.listeners = listeners
    this.parent = parent
    reloadItems()
  }

  fun reloadItems(): Unit = updateItems(parent.children())

  fun updateItems(newChildren: List<LiveFile>) {
    items.postValue(FileSorter.loadExplorerList(
      resources,
      parent,
      newChildren,
      listOptions,
      listeners,
    ))
  }

  fun getItems(): LiveData<List<Any>> = items

}
