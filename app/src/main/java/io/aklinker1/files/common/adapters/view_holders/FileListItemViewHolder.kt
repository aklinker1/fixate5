package io.aklinker1.files.common.adapters.view_holders

import android.util.Log
import android.view.View
import android.view.ViewGroup
import io.aklinker1.files.R
import io.aklinker1.files.common.base.BaseViewHolder
import io.aklinker1.files.common.glide.FileInfo
import io.aklinker1.files.common.glide.GlideApp
import io.aklinker1.files.common.models.FileListItem
import io.aklinker1.files.common.utils.fs.KnownFileIcons
import io.aklinker1.files.databinding.FileListItemBinding

class FileListItemViewHolder(viewGroup: ViewGroup) :
  BaseViewHolder<FileListItem>(viewGroup, R.layout.file_list_item) {
  private val binding = FileListItemBinding.bind(itemView)

  override fun onBind(data: FileListItem) {
    binding.apply {
      details.checkbox.visibility = if (data.multiselectMode) View.VISIBLE else View.GONE
      details.checkbox.isChecked = data.multiselectMode && data.isSelected

      details.menu.visibility = View.GONE // if (folder.multiselectMode) View.GONE else View.VISIBLE

      val extensionInfo = FileInfo.extensionInfos[data.file.extensionNoDot()] ?: FileInfo.defaultExtensionInfo(itemView.context)
      Log.d("fixate", "extension: " + extensionInfo.mimeType)
      details.icon.setImageResource(extensionInfo.typeIcon)
      preview.setBackgroundResource(extensionInfo.color ?: android.R.color.transparent)
      GlideApp.with(itemView.context).clear(preview)
      GlideApp.with(itemView.context)
        .load(data.file)
        .centerCrop()
        .into(preview)

      details.name.text = data.displayName

      click.setOnClickListener { data.onClick(data.file) }
    }
  }
}
