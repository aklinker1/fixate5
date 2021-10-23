package io.aklinker1.files

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
  viewGroup: ViewGroup,
  @LayoutRes layout: Int,
  attachToRoot: Boolean = false,
) : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context)
  .inflate(layout, viewGroup, attachToRoot)) {

  abstract fun onBind(data: T)

}
