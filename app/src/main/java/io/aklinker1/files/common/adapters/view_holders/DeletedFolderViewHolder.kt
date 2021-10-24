package io.aklinker1.files.common.adapters.view_holders

import android.view.ViewGroup
import io.aklinker1.files.R
import io.aklinker1.files.common.base.BaseViewHolder
import io.aklinker1.files.common.models.DeletedFolder
import io.aklinker1.files.common.models.NoFiles
import io.aklinker1.files.common.utils.makeFullWidth
import io.aklinker1.files.databinding.DeletedFolderBinding
import io.aklinker1.files.databinding.NoFilesBinding

class DeletedFolderViewHolder(viewGroup: ViewGroup) :
  BaseViewHolder<DeletedFolder>(viewGroup, R.layout.deleted_folder) {
  private val binding = DeletedFolderBinding.bind(itemView)

  override fun onBind(data: DeletedFolder) {
    makeFullWidth(this)
    binding.header.parentName.text = data.displayName
  }

}
