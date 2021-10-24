package io.aklinker1.files.common.base

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.aklinker1.files.BaseActivity

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

  protected val baseActivity: BaseActivity
    get() = requireActivity() as BaseActivity

}
