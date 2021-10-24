package io.aklinker1.files.common.adapters.view_holders

import android.view.View
import android.view.ViewGroup
import io.aklinker1.files.common.base.BaseViewHolder
import io.aklinker1.files.R
import io.aklinker1.files.common.models.NavigationPath
import io.aklinker1.files.databinding.NavigationPathBinding

class NavigationPathViewHolder(viewGroup: ViewGroup) :
  BaseViewHolder<NavigationPath>(viewGroup, R.layout.navigation_path) {
  private val binding = NavigationPathBinding.bind(itemView)

  override fun onBind(data: NavigationPath) {
    binding.apply {
      name.text = data.filename
      icon.setImageResource(data.icon ?: android.R.color.transparent)
      icon.visibility = if (data.icon != null) View.VISIBLE else View.GONE
      chevron.visibility = if (data.hasDividerLeft) View.VISIBLE else View.GONE

      val color = itemView.resources.getColor(
        if (data.isActive) R.color.navigation_active else R.color.navigation_inactive,
      )
      val textAlpha = if (data.isActive) 1f else 0.42f
      val iconAlpha = if (data.isActive) 1f else 0.38f
      name.setTextColor(color)
      name.alpha = textAlpha
      icon.setColorFilter(color)
      icon.alpha = iconAlpha
      chevron.setColorFilter(color)
      chevron.alpha = iconAlpha

      click.setOnClickListener { data.onClick(data.index) }
    }
  }
}
