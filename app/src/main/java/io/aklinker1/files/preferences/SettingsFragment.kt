package io.aklinker1.files.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import io.aklinker1.files.R

class SettingsFragment : PreferenceFragmentCompat() {
  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.preferences, rootKey)

  }

}
