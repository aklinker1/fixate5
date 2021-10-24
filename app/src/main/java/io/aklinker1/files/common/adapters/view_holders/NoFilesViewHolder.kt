package io.aklinker1.files.common.adapters.view_holders

import android.view.ViewGroup
import io.aklinker1.files.R
import io.aklinker1.files.common.base.BaseViewHolder
import io.aklinker1.files.common.models.NoFiles
import io.aklinker1.files.common.utils.makeFullWidth
import io.aklinker1.files.databinding.NoFilesBinding

class NoFilesViewHolder(viewGroup: ViewGroup) :
  BaseViewHolder<NoFiles>(viewGroup, R.layout.no_files) {
  private val binding = NoFilesBinding.bind(itemView)

  override fun onBind(data: NoFiles) {
    makeFullWidth(this)
    binding.header.parentName.text = data.displayName
  }

}
