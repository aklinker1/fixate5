package io.aklinker1.files.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class WeightedTextView : AppCompatTextView {

  enum class Weight(val string: String) {
    THIN("Thin"),
    LIGHT("Light"),
    REGULAR("Regular"),
    MEDIUM("Medium"),
    BOLD("Bold"),
    BLACK("Black"),
  }

  companion object {
    const val DEFAULT_FONT_NAME = "Roboto"
    const val DEFAULT_WEIGHT = 2 // Ordinal for REGULAR
    const val DEFAULT_IS_ITALIC = false
  }

  private var mFontName: String? = null
  private var mWeight: Weight = Weight.REGULAR
  private var mIsItalic: Boolean = false

  var fontName: String?
    get() = this.mFontName
    set(fontName) {
      this.mFontName = fontName
      updateFont()
    }

  var weight: Weight
    get() = mWeight
    set(weight) {
      this.mWeight = weight
      updateFont()
    }

  var isItalic: Boolean
    get() = mIsItalic
    set(isItalic) {
      this.mIsItalic = isItalic
      updateFont()
    }

  constructor(context: Context) : super(context) {
    init(context, null)
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init(context, attrs)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    init(context, attrs)
  }

  private fun init(context: Context, attrs: AttributeSet?) {
    if (attrs != null) {
      val a = context.obtainStyledAttributes(attrs, R.styleable.WeightedTextView)
      mFontName = a.getString(R.styleable.WeightedTextView_font_name)
      if (mFontName == null || mFontName!!.isEmpty()) mFontName = DEFAULT_FONT_NAME
      val weightIndex = a.getInt(R.styleable.WeightedTextView_font_weight, DEFAULT_WEIGHT)
      mWeight = Weight.values()[weightIndex]
      mIsItalic = a.getBoolean(R.styleable.WeightedTextView_italicize, DEFAULT_IS_ITALIC)
    } else {
      mFontName = DEFAULT_FONT_NAME
      mWeight = Weight.REGULAR
      mIsItalic = DEFAULT_IS_ITALIC
    }

    updateFont()
  }

  private fun updateFont() {
    if (mFontName != null) {
      typeface = FontCache.getTypeface(context, mFontName!!, mWeight.string, mIsItalic)
    }
    invalidate()
  }

}
