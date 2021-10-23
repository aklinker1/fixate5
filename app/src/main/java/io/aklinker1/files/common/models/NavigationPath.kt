package io.aklinker1.files.common.models

import androidx.annotation.DrawableRes
import io.aklinker1.livefs.LiveFile

data class NavigationPath(
  val fullPath: String,
  val filename: String,
  val folder: LiveFile,
  val hasDividerLeft: Boolean,
  @DrawableRes val icon: Int?,
  val isActive: Boolean,
  val index: Int,
  val onClick: OnNavigationPathClick,
) {
  /**
   * For `FixateAdapter.getItemId`
   */
  override fun hashCode(): Int {
    return "NavigationPath-$index".hashCode()
  }
}
