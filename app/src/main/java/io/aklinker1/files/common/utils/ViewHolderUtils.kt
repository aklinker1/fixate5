package io.aklinker1.files.common.utils

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.aklinker1.files.common.base.BaseViewHolder

fun <T> makeFullWidth(viewHolder: BaseViewHolder<T>) {
  val params = viewHolder.itemView.layoutParams
  if (params is StaggeredGridLayoutManager.LayoutParams) {
    params.isFullSpan = true
    viewHolder.itemView.layoutParams = params
  }
}
