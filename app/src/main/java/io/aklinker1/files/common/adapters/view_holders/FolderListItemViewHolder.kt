package io.aklinker1.files.common.adapters.view_holders

import android.view.View
import android.view.ViewGroup
import io.aklinker1.files.BaseViewHolder
import io.aklinker1.files.R
import io.aklinker1.files.common.models.FolderListItem
import io.aklinker1.files.common.utils.fs.KnownFileIcons
import io.aklinker1.files.databinding.FolderListItemBinding

class FolderListItemViewHolder(viewGroup: ViewGroup) :
  BaseViewHolder<FolderListItem>(viewGroup, R.layout.folder_list_item) {
  private val binding = FolderListItemBinding.bind(itemView)

  override fun onBind(data: FolderListItem) {
    binding.apply {
      checkbox.visibility = if (data.multiselectMode) View.VISIBLE else View.GONE
      checkbox.isChecked = data.multiselectMode && data.isSelected

      menu.visibility = View.GONE // if (folder.multiselectMode) View.GONE else View.VISIBLE

      icon.setImageResource(KnownFileIcons[data.folder.path] ?: R.drawable.folder_default)

      name.text = data.displayName

      click.setOnClickListener { data.onClick(data.folder) }
    }
  }
}
