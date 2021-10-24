package io.aklinker1.files.preferences

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.aklinker1.files.common.base.BaseActivity
import io.aklinker1.files.R
import io.aklinker1.files.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity() {
  companion object {
    fun open(activity: BaseActivity) {
      activity.startActivity(Intent(activity, SettingsActivity::class.java))
    }
  }

  lateinit var binding: SettingsActivityBinding

  override fun onInflateView(): View {
    binding = SettingsActivityBinding.inflate(this.layoutInflater)
    return binding.root
  }

  override fun onInit(savedInstanceState: Bundle?) {
    binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    supportFragmentManager.beginTransaction().replace(R.id.settings_container, SettingsFragment())
      .commitNow()
  }

}
