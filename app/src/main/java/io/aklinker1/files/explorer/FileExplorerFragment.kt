package io.aklinker1.files.explorer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import io.aklinker1.files.BaseFragment
import io.aklinker1.files.common.models.NavigationPath
import io.aklinker1.files.common.view_models.NavigationViewModel
import io.aklinker1.files.databinding.FileExplorerFragmentBinding
import io.aklinker1.files.file_list.FileListFragment

class FileExplorerFragment : BaseFragment() {

  private inner class ScreenSlidePagerAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    var items: List<NavigationPath> = listOf()
    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment = FileListFragment(items[position].folder)

    override fun getItemId(position: Int): Long {
      return items[position].fullPath.hashCode().toLong()
    }
  }

  lateinit var binding: FileExplorerFragmentBinding
  lateinit var navigationViewModel: NavigationViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    binding = FileExplorerFragmentBinding.inflate(layoutInflater)
    navigationViewModel = NavigationViewModel.from(baseActivity)
    initLayout()
    return binding.root
  }

  @SuppressLint("NotifyDataSetChanged", "ResourceType")
  private fun initLayout() {
    val adapter = ScreenSlidePagerAdapter(baseActivity)
    binding.viewPager.adapter = adapter

    val items = navigationViewModel.getPathItems()
    val activeIndex = navigationViewModel.getActivePathIndex()

    items.observe(viewLifecycleOwner) {
      Log.d("fixate", "items changed: $it")
      adapter.items = it
      adapter.notifyDataSetChanged()
    }
    activeIndex.observe(viewLifecycleOwner) {
      binding.viewPager.setCurrentItem(it, true)
    }
    binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        navigationViewModel.jump(position)
      }
    })
  }

}
