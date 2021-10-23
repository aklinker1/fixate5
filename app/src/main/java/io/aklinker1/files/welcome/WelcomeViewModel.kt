package io.aklinker1.files.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.aklinker1.files.BaseActivity
import io.aklinker1.files.BaseViewModel
import io.aklinker1.files.common.repos.PermissionsRepo

class WelcomeViewModel(activity: BaseActivity) : BaseViewModel() {

  private val permissionsRepo = PermissionsRepo(activity)

  private val hasGrantedFilePermissionCompat: MutableLiveData<Boolean> by lazy {
    MutableLiveData(permissionsRepo.hasGrantedFilePermissionCompat())
  }

  init {
    permissionsRepo.addPermissionCallback { isGranted ->
      hasGrantedFilePermissionCompat.postValue(isGranted)
    }
  }

  fun checkPermission() {
    hasGrantedFilePermissionCompat.postValue(permissionsRepo.hasGrantedFilePermissionCompat())
  }

  fun getHasGrantedFilePermission(): LiveData<Boolean> {
    return hasGrantedFilePermissionCompat
  }

  fun requestFilePermissionCompat() {
    permissionsRepo.requestFilePermissionCompat()
  }

}
