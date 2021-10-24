package io.aklinker1.files.common.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity

abstract class BaseActivity : FragmentActivity() {

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
    setContentView(this.onInflateView())
    onInit(savedInstanceState)
  }

}
