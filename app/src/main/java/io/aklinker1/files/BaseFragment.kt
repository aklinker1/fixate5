package io.aklinker1.files

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

  protected val baseActivity: BaseActivity
    get() = requireActivity() as BaseActivity

}
