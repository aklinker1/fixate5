package io.aklinker1.files.preferences

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.aklinker1.files.common.base.BaseViewModel
import io.aklinker1.files.common.enums.GroupBy
import io.aklinker1.files.common.enums.SortBy
import io.aklinker1.files.common.repos.SettingsRepo

class SettingsFragmentViewModel(activity: Activity) : BaseViewModel() {
  private val settingsRepo = SettingsRepo(activity)

  private val dayNightMode: MutableLiveData<Int> by lazy {
    MutableLiveData(settingsRepo.dayNightMode)
  }
  fun getDayNightMode(): LiveData<Int> = dayNightMode
  fun setDayNightMode(value: Int) {
    settingsRepo.dayNightMode = value
    dayNightMode.postValue(value)
  }

  private val fileSort: MutableLiveData<SortBy> by lazy {
    MutableLiveData(settingsRepo.fileSort)
  }
  fun getFileSort(): LiveData<SortBy> = fileSort
  fun setFileSort(value: SortBy) {
    settingsRepo.fileSort = value
    fileSort.postValue(value)
  }

  private val groupBy: MutableLiveData<GroupBy> by lazy {
    MutableLiveData(settingsRepo.groupBy)
  }
  fun getGroupBy(): LiveData<GroupBy> = groupBy
  fun setGroupBy(value: GroupBy) {
    settingsRepo.groupBy = value
    groupBy.postValue(value)
  }

  private val foldersFirst: MutableLiveData<Boolean> by lazy {
    MutableLiveData(settingsRepo.foldersFirst)
  }
  fun getFoldersFirst(): LiveData<Boolean> = foldersFirst
  fun setFoldersFirst(value: Boolean) {
    settingsRepo.foldersFirst = value
    foldersFirst.postValue(value)
  }

}
