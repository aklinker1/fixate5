package io.aklinker1.files.ui

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import java.util.*

object FontCache {

  private val cache = HashMap<String, Typeface>()

  fun getTypeface(
    context: Context,
    fontName: String,
    weight: String,
    isItalic: Boolean,
  ): Typeface? {
    val filename = "fonts/" + fontName + "-" + weight + (if (isItalic) "-Italic" else "") + ".ttf"

    var font = cache[filename]
    if (font == null) {
      try {
        font = Typeface.createFromAsset(context.assets, filename)
      } catch (e: Exception) {
        Log.d("ui",
          "WeightedTextView Error - typeface value combos don't have a matching font in the assets/fonts/ directory")
        return Typeface.DEFAULT
      }

    }
    return font
  }

}
