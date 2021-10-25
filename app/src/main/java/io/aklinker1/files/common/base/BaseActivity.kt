package io.aklinker1.files.common.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import io.aklinker1.files.R
import io.aklinker1.files.common.repos.SettingsRepo

abstract class BaseActivity : FragmentActivity() {

  private var previousDayNightMode: Int? = null

  /**
   * Initialize the view binding and return it's root View
   */
  abstract fun onInflateView(): View

  /**
   * Set initial values on the bound views
   */
  abstract fun onInit(savedInstanceState: Bundle?)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // val mode = getDayNightMode()
    // Log.d("fixate", "BaseActivity.onCreate: mode=$mode")
    // AppCompatDelegate.setDefaultNightMode(mode)
    // previousDayNightMode = mode
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    setContentView(this.onInflateView())
    onInit(savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    // val newDayNightMode = getDayNightMode()
    // TODO: Can I change this back and forth no problem?
    // if (newDayNightMode != previousDayNightMode)
    //   // This updates the previous value because the activity is restarted
    //   recreate()
  }

  private fun getDayNightMode(): Int =
    getSharedPreferences(SettingsRepo.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(
      resources.getString(R.string.preference_day_night_mode_key),
      AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
    )

}
