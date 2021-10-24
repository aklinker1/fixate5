package io.aklinker1.files.preferences

import android.os.Bundle
import android.util.Log
import androidx.preference.DropDownPreference
import androidx.preference.PreferenceFragmentCompat
import io.aklinker1.files.R
import io.aklinker1.files.common.enums.SortBy

class SettingsFragment : PreferenceFragmentCompat() {

  lateinit var viewModel: SettingsFragmentViewModel

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.preferences, rootKey)
    viewModel = SettingsFragmentViewModel(requireActivity())
    initLayout()
  }

  private fun initLayout() {
    val fileSort = viewModel.getFileSort()
    val fileSortPref =
      findPreference<DropDownPreference>(getString(R.string.preference_file_sort_key))
        ?: throw RuntimeException("File sort pref was not found")
    fileSort.observe(this) {
      Log.d("fixate", "initLayout: observer ${it.name}")
      fileSortPref.summary = fileSortPref.entries[fileSortPref.findIndexOfValue(it.name)]
    }
    fileSortPref.setOnPreferenceChangeListener { _, newValue ->
      Log.d("fixate", "initLayout: pref listener $newValue")
      viewModel.setFileSort(SortBy.valueOf(newValue as String))
      true
    }
  }

}
