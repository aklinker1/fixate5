package io.aklinker1.files.common.repos

import android.app.Activity
import io.aklinker1.files.R
import io.aklinker1.files.common.enums.SortBy

class SettingsRepo(activity: Activity): SharedPrefsRepo(activity, SHARED_PREFERENCES_NAME) {
  companion object {
    const val SHARED_PREFERENCES_NAME = "user-settings"
  }

  private val fileSortKey = activity.resources.getString(R.string.preference_file_sort_key)
  var fileSort: SortBy
    get() = getEnum(fileSortKey, SortBy.FILENAME_ASC)
    set(value) = putEnum(fileSortKey, value)

}
