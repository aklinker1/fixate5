package io.aklinker1.files.main_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.aklinker1.files.BuildConfig
import io.aklinker1.files.R
import io.aklinker1.files.common.base.BaseBottomSheetDialogFragment
import io.aklinker1.files.common.utils.fs.KnownFilePaths
import io.aklinker1.files.common.view_models.NavigationViewModel
import io.aklinker1.files.databinding.MainMenuFragmentBinding
import io.aklinker1.files.preferences.SettingsActivity
import io.aklinker1.livefs.LiveFile

class MainMenuFragment : BaseBottomSheetDialogFragment() {
  lateinit var binding: MainMenuFragmentBinding
  lateinit var navigationViewModel: NavigationViewModel
  lateinit var mainMenuViewModel: MainMenuViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    binding = MainMenuFragmentBinding.inflate(inflater)
    mainMenuViewModel = MainMenuViewModel.from(baseActivity)
    navigationViewModel = NavigationViewModel.from(baseActivity)
    initLayout()
    return binding.root
  }

  private fun initLayout() {
    val currentMenuItem = mainMenuViewModel.getCurrentMenuItem()

    // Views
    var subtitleText = BuildConfig.VERSION_NAME
    if (BuildConfig.FLAVOR == "beta") {
      subtitleText = "Beta, $subtitleText"
    }
    binding.subtitle.text = subtitleText

    // Listeners
    binding.navHome.setOnClickListener {
      mainMenuViewModel.setCurrentMenuItem(it.id)
      if (currentMenuItem.value != it.id) navigationViewModel.reset(LiveFile(KnownFilePaths.HOME))
      dismiss()
    }
    binding.navTrash.setOnClickListener {
      mainMenuViewModel.setCurrentMenuItem(it.id)
      val trash = LiveFile(KnownFilePaths.TRASH)
      if (!trash.file.exists()) trash.file.mkdirs()
      if (currentMenuItem.value != it.id) navigationViewModel.reset(trash)
      dismiss()
    }
    binding.navSettings.setOnClickListener {
      SettingsActivity.open(baseActivity)
      dismiss()
    }

    // Observers
    currentMenuItem.observe(this) {
      binding.navHome.setIsSelected(it == R.id.nav_home)
      binding.navTrash.setIsSelected(it == R.id.nav_trash)
    }
  }
}
