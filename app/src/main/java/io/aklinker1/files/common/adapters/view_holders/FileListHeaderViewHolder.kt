package io.aklinker1.files.common.adapters.view_holders

import android.view.ViewGroup
import io.aklinker1.files.BaseViewHolder
import io.aklinker1.files.R
import io.aklinker1.files.common.models.FileListHeader
import io.aklinker1.files.common.utils.fs.KnownFileAliases
import io.aklinker1.files.common.utils.makeFullWidth
import io.aklinker1.files.databinding.FileListHeaderBinding

class FileListHeaderViewHolder(viewGroup: ViewGroup) :
  BaseViewHolder<FileListHeader>(viewGroup, R.layout.file_list_header) {
  private val binding = FileListHeaderBinding.bind(itemView)

  override fun onBind(data: FileListHeader) {
    makeFullWidth(this)

    val alias = KnownFileAliases[data.path]
    if (alias == null) {
      binding.parentName.text = data.displayName
    } else {
      binding.parentName.setText(alias)
    }
  }

}
