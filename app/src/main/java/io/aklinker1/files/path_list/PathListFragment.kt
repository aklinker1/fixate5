package io.aklinker1.files.path_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.aklinker1.files.BaseFragment
import io.aklinker1.files.common.adapters.list.FixateAdapter
import io.aklinker1.files.common.models.FileListItemClickListeners
import io.aklinker1.files.common.view_models.NavigationViewModel
import io.aklinker1.files.databinding.PathListFragmentBinding
import io.aklinker1.livefs.LiveFile

class PathListFragment(val initialRoot: LiveFile) : BaseFragment() {

  lateinit var binding: PathListFragmentBinding
  lateinit var navigationViewModel: NavigationViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    binding = PathListFragmentBinding.inflate(inflater)
    navigationViewModel = NavigationViewModel.from(baseActivity)
    initLayout()
    return binding.root
  }

  @SuppressLint("NotifyDataSetChanged", "ResourceType")
  private fun initLayout() {
    navigationViewModel.init(resources,
      FileListItemClickListeners(onNavigationPathClick = { navigationViewModel.jump(it) }))
    navigationViewModel.reset(initialRoot)
    val pathAdapter = FixateAdapter()
    pathAdapter.setHasStableIds(true)
    binding.pathList.apply {
      adapter = pathAdapter
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
    val pathItems = navigationViewModel.getPathItems()
    val activePathIndex = navigationViewModel.getActivePathIndex()
    pathItems.observe(viewLifecycleOwner) {
      pathAdapter.items = it
      pathAdapter.notifyDataSetChanged()
    }
    activePathIndex.observe(viewLifecycleOwner) { binding.pathList.smoothScrollToPosition(it) }
  }

}
