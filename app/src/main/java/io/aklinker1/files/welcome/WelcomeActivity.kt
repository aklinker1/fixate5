package io.aklinker1.files.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.aklinker1.files.BaseActivity
import io.aklinker1.files.databinding.WelcomeActivityBinding
import io.aklinker1.files.main.MainActivity

class WelcomeActivity : BaseActivity() {

  companion object {
    fun open(activity: BaseActivity, resetNavigationStack: Boolean = true) {
      val intent = Intent(activity, WelcomeActivity::class.java).apply {
        if (resetNavigationStack) flags =
          Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      }
      activity.startActivity(intent)
    }
  }

  private lateinit var binding: WelcomeActivityBinding
  private lateinit var viewModel: WelcomeViewModel

  override fun onInflateView(): View {
    binding = WelcomeActivityBinding.inflate(layoutInflater)
    return binding.root
  }

  override fun onInit(savedInstanceState: Bundle?) {
    viewModel = WelcomeViewModel(this)

    binding.grant.setOnClickListener {
      viewModel.requestFilePermissionCompat()
    }
    binding.letsGo.setOnClickListener { MainActivity.open(this) }

    viewModel.getHasGrantedFilePermission().observe(this) { isGranted ->
      binding.grant.apply {
        isEnabled = !isGranted
        text = if (isGranted) "Access Granted" else "Grant Access"
      }
      binding.letsGo.apply {
        isEnabled = isGranted
        alpha = if (isGranted) 1f else 0f
      }
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.checkPermission()
  }
}
