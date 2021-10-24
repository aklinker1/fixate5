package io.aklinker1.files.common.repos

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import io.aklinker1.files.common.base.BaseActivity
import io.aklinker1.files.common.enums.SerializableEnum

abstract class SharedPrefsRepo(activity: Activity, sharedPreferencesName: String) {
  protected val prefs: SharedPreferences = activity.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)!!

  // Get Utils

  protected fun getBoolean(key: String, default: Boolean = false): Boolean {
    return prefs.getBoolean(key, default)
  }

  protected fun getString(key: String, default: String): String {
    return prefs.getString(key, default) ?: default
  }

  protected fun <T : Enum<T>> getEnum(key: String, default: SerializableEnum<T>): T {
    return default.from(getString(key, default.write()))
  }

  // Put Utils

  protected fun putString(key: String, value: String) {
    prefs.edit().putString(key, value).apply()
  }

  protected fun <T> putEnum(key: String, value: SerializableEnum<T>) {
    putString(key, value.write())
  }

}
