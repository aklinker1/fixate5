package io.aklinker1.files.common.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.animation.AnimationUtils

class HideBottomAppBarBehavior<V : View>(private val context: Context, attrs: AttributeSet) :
  CoordinatorLayout.Behavior<V>(context, attrs) {

  companion object {
    private const val ENTER_ANIMATION_DURATION = 300L
    private const val EXIT_ANIMATION_DURATION = 250L
    private const val STATE_SCROLLED_DOWN = 1
    private const val STATE_SCROLLED_UP = 2
  }

  private var height = 0
  private var currentState = 2
  private var currentAnimator: ViewPropertyAnimator? = null

  override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
    this.height = ResourceUtils.appBarSize(context)
    return super.onLayoutChild(parent, child, layoutDirection)
  }

  override fun onStartNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: V,
    directTargetChild: View,
    target: View,
    nestedScrollAxes: Int,
  ): Boolean {
    return nestedScrollAxes == 2
  }

  override fun onNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: V,
    target: View,
    dxConsumed: Int,
    dyConsumed: Int,
    dxUnconsumed: Int,
    dyUnconsumed: Int,
  ) {
    if (this.currentState != STATE_SCROLLED_DOWN && dyConsumed > 0) {
      this.slideDown(child)
    } else if (this.currentState != STATE_SCROLLED_UP && dyConsumed < 0) {
      this.slideUp(child)
    }

  }

  fun slideUp(child: V) {
    if (this.currentAnimator != null) {
      this.currentAnimator!!.cancel()
      child.clearAnimation()
    }

    this.currentState = STATE_SCROLLED_UP
    this.animateChildTo(child,
      0,
      ENTER_ANIMATION_DURATION,
      AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
  }

  fun slideDown(child: V) {
    if (this.currentAnimator != null) {
      this.currentAnimator!!.cancel()
      child.clearAnimation()
    }

    this.currentState = STATE_SCROLLED_DOWN
    this.animateChildTo(child,
      this.height,
      EXIT_ANIMATION_DURATION,
      AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR)
  }

  private fun animateChildTo(
    child: V,
    targetY: Int,
    duration: Long,
    interpolator: TimeInterpolator,
  ) {
    this.currentAnimator = child.animate().apply {
      translationY(targetY.toFloat())
      setInterpolator(interpolator)
      setDuration(duration)
      startDelay = 150L
      setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          this@HideBottomAppBarBehavior.currentAnimator = null
        }
      })
    }
  }
}
