package io.aklinker1.files.common.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.Px


object ResourceUtils {

  @Px
  fun appBarSize(context: Context): Int {
    val tv = TypedValue()
    if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      return TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
    }
    return (54 * context.resources.displayMetrics.density).toInt()
  }

}
