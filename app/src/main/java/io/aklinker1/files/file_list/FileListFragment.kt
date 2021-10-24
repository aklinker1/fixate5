package io.aklinker1.files.file_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.aklinker1.files.BaseActivity
import io.aklinker1.files.BaseFragment
import io.aklinker1.files.R
import io.aklinker1.files.common.adapters.list.FixateAdapter
import io.aklinker1.files.common.models.FileListItemClickListeners
import io.aklinker1.files.common.view_models.NavigationViewModel
import io.aklinker1.files.databinding.FileListFragmentBinding
import io.aklinker1.livefs.LiveFile

class FileListFragment(private var parent: LiveFile) : BaseFragment() {

  lateinit var binding: FileListFragmentBinding
  lateinit var fileListViewModel: FileListViewModel
  lateinit var navigationViewModel: NavigationViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    binding = FileListFragmentBinding.inflate(inflater)
    fileListViewModel = FileListViewModel.from(this)
    navigationViewModel = NavigationViewModel.from(baseActivity)
    initLayout()
    return binding.root
  }

  @SuppressLint("NotifyDataSetChanged", "ResourceType")
  private fun initLayout() {
    val items = fileListViewModel.getItems()
    val children = parent.liveChildren()

    // Initialize
    fileListViewModel.init(
      this.resources,
      parent,
      FileListItemClickListeners(
        onFolderListItemClick = { navigationViewModel.push(it) },
      ),
    )
    val adapter = FixateAdapter(items.value ?: listOf())
    adapter.setHasStableIds(true)
    binding.list.apply {
      layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
      setAdapter(adapter)
    }
    binding.refresher.setColorSchemeResources(R.color.blue_500) // TODO: Make lighter blue in dark mode
    binding.refresher.setOnRefreshListener {
      fileListViewModel.reloadItems()
    }

    // Listen
    items.observe(baseActivity) {
      adapter.items = it
      adapter.notifyDataSetChanged()
      binding.refresher.isRefreshing = false
    }
    children.observe(baseActivity) { fileListViewModel.updateItems(it) }
  }

  val id: Long
    get() = parent.path.hashCode().toLong()
}
