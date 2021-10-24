package io.aklinker1.files.common.view_models

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import io.aklinker1.files.common.base.BaseViewModel
import io.aklinker1.files.common.models.FileListItemClickListeners
import io.aklinker1.files.common.models.NavigationPath
import io.aklinker1.files.common.utils.fs.KnownFileIcons
import io.aklinker1.files.common.utils.fs.nameOrAlias
import io.aklinker1.livefs.LiveFile

class NavigationViewModel @Deprecated("Use NavigationViewModel.from() instead") constructor() :
  BaseViewModel() {
  companion object {
    fun from(owner: ViewModelStoreOwner) = ViewModelProvider(owner)[NavigationViewModel::class.java]
  }

  private val pathItems: MutableLiveData<ArrayList<NavigationPath>> by lazy {
    MutableLiveData(arrayListOf())
  }
  private val activePathIndex: MutableLiveData<Int> by lazy {
    MutableLiveData(0)
  }
  private lateinit var resources: Resources
  private var listeners: FileListItemClickListeners? = null

  fun init(resources: Resources, listeners: FileListItemClickListeners? = null) {
    this.resources = resources
    this.listeners = listeners
  }

  fun getPathItems(): LiveData<ArrayList<NavigationPath>> = pathItems
  fun getActivePathIndex(): LiveData<Int> = activePathIndex

  fun reset(root: LiveFile) {
    val firstPath = NavigationPath(
      index = 0,
      fullPath = root.path,
      filename = root.nameOrAlias(resources),
      folder = root,
      icon = KnownFileIcons[root.path],
      hasDividerLeft = false,
      isActive = true,
      onClick = listeners?.onNavigationPathClick ?: TODO("onNavigationPathClick must be passed"),
    )
    pathItems.value = arrayListOf(firstPath)
    activePathIndex.value = 0
  }

  fun push(folder: LiveFile) {
    val currentItems = pathItems.value?.clone() as ArrayList<NavigationPath>? ?: arrayListOf()
    val i = activePathIndex.value ?: 0
    val newI = i + 1
    val newItem = NavigationPath(
      index = newI,
      fullPath = folder.path,
      filename = folder.nameOrAlias(resources),
      folder = folder,
      icon = KnownFileIcons[folder.path],
      hasDividerLeft = true,
      isActive = true,
      onClick = listeners?.onNavigationPathClick ?: TODO("onNavigationPathClick must be passed"),
    )

    when {
      // The item is being added to the top of the stack
      currentItems.size <= newI -> {
        currentItems.add(newItem)
        pathItems.postValue(currentItems)
        activePathIndex.postValue(newI)
      }
      // There are existing items left on top from popping
      currentItems.size > newI -> {
        val newItems = ArrayList<NavigationPath>(newI)
        newItems.addAll(currentItems.subList(0, newI))
        newItems.add(newItem)
        pathItems.postValue(newItems)
        activePathIndex.postValue(newI)
      }
      else -> TODO("Unhandled push file path case: currentItems.size=${currentItems.size}, i=$i")
    }
  }

  fun pop() {
    val i = activePathIndex.value ?: 0
    if (i != 0) {
      var newIndex = i - 1
      val currentItems = pathItems.value?.clone() as ArrayList<NavigationPath>? ?: arrayListOf()
      val newItems = ArrayList<NavigationPath>(currentItems.size)
      currentItems.forEachIndexed { j, item ->
        if (item.folder.file.exists()) newItems.add(item.copy(isActive = j <= newIndex))
      }
      if (newIndex >= newItems.size) newIndex = newItems.size - 1
      activePathIndex.postValue(newIndex)
      pathItems.postValue(newItems)
    }
  }

  fun jump(newActiveIndex: Int) {
    if (newActiveIndex == activePathIndex.value) return

    var newIndex = newActiveIndex
    val currentItems = pathItems.value?.clone() as ArrayList<NavigationPath>? ?: arrayListOf()
    val newItems = ArrayList<NavigationPath>(currentItems.size)
    currentItems.forEachIndexed { j, item ->
      if (item.folder.file.exists()) newItems.add(item.copy(isActive = j <= newIndex))
    }
    if (newIndex >= newItems.size) newIndex = newItems.size - 1
    activePathIndex.postValue(newIndex)
    pathItems.postValue(newItems)
  }

}
