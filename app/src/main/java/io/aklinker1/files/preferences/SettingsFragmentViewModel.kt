package io.aklinker1.files.preferences

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.aklinker1.files.common.base.BaseViewModel
import io.aklinker1.files.common.enums.SortBy
import io.aklinker1.files.common.repos.SettingsRepo

class SettingsFragmentViewModel(activity: Activity) : BaseViewModel() {
  private val settingsRepo = SettingsRepo(activity)

  private val fileSort: MutableLiveData<SortBy> by lazy {
    MutableLiveData(settingsRepo.fileSort)
  }
  fun getFileSort(): LiveData<SortBy> = fileSort
  fun setFileSort(value: SortBy) {
    settingsRepo.fileSort = value
    fileSort.postValue(value)
  }

}
