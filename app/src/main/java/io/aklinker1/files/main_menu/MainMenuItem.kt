package io.aklinker1.files.main_menu

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import io.aklinker1.files.R
import io.aklinker1.files.databinding.MainMenuItemBinding
import io.aklinker1.files.ui.WeightedTextView

class MainMenuItem(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

  lateinit var binding: MainMenuItemBinding

  private val title: String?
  private val icon: Drawable?

  init {
    // Set Attrs
    var isSelected = false
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.MainMenuItem,
      0, 0
    ).apply {

      try {
        title = getString(R.styleable.MainMenuItem_title)
        icon = getDrawable(R.styleable.MainMenuItem_icon)
        isSelected = getBoolean(R.styleable.MainMenuItem_selected, false)
      } finally {
        recycle()
      }
    }

    // Setup Views
    View.inflate(context, R.layout.main_menu_item, this)
    binding = MainMenuItemBinding.bind(this)

    // Set Values
    binding.title.text = title
    binding.icon.setImageDrawable(icon)
    setIsSelected(isSelected)
  }

  fun setIsSelected(isSelected: Boolean) {
    binding.title.isSelected = isSelected
    binding.title.weight =
      if (isSelected) WeightedTextView.Weight.MEDIUM else WeightedTextView.Weight.REGULAR
    binding.icon.isSelected = isSelected
    this.isSelected = isSelected
  }

}
