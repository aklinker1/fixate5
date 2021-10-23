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

  override fun onBind(folder: FolderListItem) {
    binding.apply {
      checkbox.visibility = if (folder.multiselectMode) View.VISIBLE else View.GONE
      checkbox.isChecked = folder.multiselectMode && folder.isSelected

      menu.visibility = View.GONE // if (folder.multiselectMode) View.GONE else View.VISIBLE

      icon.setImageResource(KnownFileIcons[folder.folder.path] ?: R.drawable.folder_default)

      name.text = folder.displayName

      click.setOnClickListener { folder.onClick(folder.folder) }
    }
  }
}
