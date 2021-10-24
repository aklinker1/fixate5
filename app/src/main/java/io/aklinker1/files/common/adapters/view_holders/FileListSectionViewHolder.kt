package io.aklinker1.files.common.adapters.view_holders

import android.view.ViewGroup
import io.aklinker1.files.common.base.BaseViewHolder
import io.aklinker1.files.R
import io.aklinker1.files.common.models.FileListSection
import io.aklinker1.files.common.utils.makeFullWidth
import io.aklinker1.files.ui.WeightedTextView

class FileListSectionViewHolder(viewGroup: ViewGroup) :
  BaseViewHolder<FileListSection>(viewGroup, R.layout.file_list_section) {
  override fun onBind(data: FileListSection) {
    makeFullWidth(this)

    (itemView as WeightedTextView).apply {
      text = data.string
    }
  }
}
