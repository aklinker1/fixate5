package io.aklinker1.files.main

import io.aklinker1.files.common.base.BaseActivity
import io.aklinker1.files.common.base.BaseViewModel
import io.aklinker1.files.common.repos.PermissionsRepo

class MainViewModel(activity: BaseActivity) : BaseViewModel() {

  private val permissionsRepo = PermissionsRepo(activity)

  fun canManageStorage(): Boolean = permissionsRepo.hasGrantedFilePermissionCompat()

}
