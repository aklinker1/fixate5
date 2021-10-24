package io.aklinker1.files.main_menu

import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import io.aklinker1.files.common.base.BaseViewModel
import io.aklinker1.files.R

class MainMenuViewModel @Deprecated("Use MainMenuViewModel.from() instead") constructor() : BaseViewModel() {
  companion object {
    fun from(owner: ViewModelStoreOwner) = ViewModelProvider(owner)[MainMenuViewModel::class.java]
  }

  private val currentMenuItem: MutableLiveData<Int> by lazy {
    MutableLiveData(R.id.nav_home)
  }

  fun getCurrentMenuItem(): LiveData<Int> = currentMenuItem

  fun setCurrentMenuItem(@IdRes selectedId: Int) {
    currentMenuItem.postValue(selectedId)
  }
}
