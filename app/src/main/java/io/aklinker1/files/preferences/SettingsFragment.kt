package io.aklinker1.files.preferences

import android.os.Bundle
import android.util.Log
import androidx.preference.DropDownPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import io.aklinker1.files.R
import io.aklinker1.files.common.enums.GroupBy
import io.aklinker1.files.common.enums.SortBy

class SettingsFragment : PreferenceFragmentCompat() {

  lateinit var viewModel: SettingsFragmentViewModel

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.preferences, rootKey)
    viewModel = SettingsFragmentViewModel(requireActivity())
    initLayout()
  }

  private fun initLayout() {
    val foldersFirst = viewModel.getFoldersFirst()
    val foldersFirstPref =
      findPreference<SwitchPreferenceCompat>(getString(R.string.preference_folders_first_key))
        ?: throw RuntimeException("Folders first pref was not found")
    foldersFirst.observe(this) {
      foldersFirstPref.isChecked = it
    }
    foldersFirstPref.setOnPreferenceChangeListener { _, newValue ->
      viewModel.setFoldersFirst(newValue as Boolean)
      true
    }

    val groupBy = viewModel.getGroupBy()
    val groupByPref =
      findPreference<SwitchPreferenceCompat>(getString(R.string.preference_group_by_key))
        ?: throw RuntimeException("Group by pref was not found")
    groupBy.observe(this) {
      groupByPref.isChecked = it == GroupBy.TYPE
      foldersFirstPref.isEnabled = it != GroupBy.TYPE
    }
    groupByPref.setOnPreferenceChangeListener { _, newValue ->
      viewModel.setGroupBy(if (newValue as Boolean) GroupBy.TYPE else GroupBy.NONE)
      true
    }

    val fileSort = viewModel.getFileSort()
    val fileSortPref =
      findPreference<DropDownPreference>(getString(R.string.preference_file_sort_key))
        ?: throw RuntimeException("File sort pref was not found")
    fileSort.observe(this) {
      fileSortPref.summary = fileSortPref.entries[fileSortPref.findIndexOfValue(it.name)]
    }
    fileSortPref.setOnPreferenceChangeListener { _, newValue ->
      viewModel.setFileSort(SortBy.valueOf(newValue as String))
      true
    }
  }

}
