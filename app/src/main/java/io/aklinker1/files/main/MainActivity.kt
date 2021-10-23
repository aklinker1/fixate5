package io.aklinker1.files.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import io.aklinker1.files.BaseActivity
import io.aklinker1.files.common.view_models.NavigationViewModel
import io.aklinker1.files.databinding.MainActivityBinding
import io.aklinker1.files.explorer.FileExplorerFragment
import io.aklinker1.files.path_list.PathListFragment
import io.aklinker1.files.welcome.WelcomeActivity
import io.aklinker1.livefs.LiveFile

class MainActivity : BaseActivity() {

  companion object {
    fun open(activity: BaseActivity) {
      val intent = Intent(activity, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      }
      activity.startActivity(intent)
    }
  }

  private lateinit var binding: MainActivityBinding
  private lateinit var mainViewModel: MainViewModel
  private lateinit var navigationViewModel: NavigationViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      window.decorView.windowInsetsController?.setSystemBarsAppearance(
        APPEARANCE_LIGHT_STATUS_BARS,
        APPEARANCE_LIGHT_STATUS_BARS,
      )
    }
    super.onCreate(savedInstanceState)
  }

  override fun onInflateView(): View {
    binding = MainActivityBinding.inflate(layoutInflater)
    return binding.root
  }

  @SuppressLint("NotifyDataSetChanged")
  override fun onInit(savedInstanceState: Bundle?) {
    mainViewModel = MainViewModel(this)
    navigationViewModel = NavigationViewModel.from(this)

    // Permissions
    if (!mainViewModel.canManageStorage()) {
      WelcomeActivity.open(this)
      return
    }

    // Load Fragments
    val initialParent = LiveFile(Environment.getExternalStorageDirectory())
    supportFragmentManager.beginTransaction()
      .replace(binding.contentContainer.id, FileExplorerFragment())
      .replace(binding.pathListContainer.id, PathListFragment(initialParent)).commit()

  }

  override fun onBackPressed() {
    if (navigationViewModel.getActivePathIndex().value != 0) {
      navigationViewModel.pop()
    } else {
      super.onBackPressed()
    }
  }
}
