package io.aklinker1.files.file_list

import android.app.Activity
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.aklinker1.files.common.base.BaseViewModel
import io.aklinker1.files.common.models.FileListItemClickListeners
import io.aklinker1.files.common.models.FileListOptions
import io.aklinker1.files.common.repos.SettingsRepo
import io.aklinker1.files.common.utils.fs.FileSorter
import io.aklinker1.livefs.LiveFile

class FileListViewModel(activity: Activity) : BaseViewModel() {

  private val settingsRepo = SettingsRepo(activity)

  private val items: MutableLiveData<List<Any>> by lazy {
    MutableLiveData(listOf())
  }

  private lateinit var parent: LiveFile
  private lateinit var resources: Resources
  private lateinit var listeners: FileListItemClickListeners

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
      FileListOptions(
        sortBy = settingsRepo.fileSort,
        groupBy = settingsRepo.groupBy,
        foldersFirst = settingsRepo.foldersFirst,
      ),
      listeners,
    ))
  }

  fun getItems(): LiveData<List<Any>> = items

}
