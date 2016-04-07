package com.easy.animate;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;

/**
 * @author Joke
 * @version 1.0.0
 * @description 展开动画，可以将任意view竖向或横向动态展开和收起。
 * @email 113979462@qq.com
 * @create 2015年5月22日
 */

public class ExtendAnimation extends ExtendAnimate {
	private ScaleAnimation sa;// 运行中的渐变动画
	private Interpolator interpolator;// 插值器，控制动画的变化快慢规律

	public ExtendAnimation(View target) {
		super(target);
	}

	public ExtendAnimation(View target, int orientation) {
		super(target, orientation);
	}

	@Override
	public void extend() {
		if (sa != null || target.getVisibility() == View.VISIBLE) {
			return;
		}
		cancelAnimate();

		float fromX = hasHorizontalOrientation() ? 0f : 1f;
		float fromY = hasVerticalOrientation() ? 0f : 1f;

		sa = new ScaleAnimation(fromX, 1f, fromY, 1f, ScaleAnimation.RELATIVE_TO_SELF,
				getPivotXValue(), ScaleAnimation.RELATIVE_TO_SELF, getPivotYValue());
		sa.setDuration(duration);
		if (interpolator != null) {
			sa.setInterpolator(interpolator);
		}

		sa.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// LogUtil.e("extend", "onAnimationStart");
				target.setVisibility(View.VISIBLE);
				if (oal != null) {
					oal.onStart();
				}
			}


			@Override
			public void onAnimationEnd(Animation animation) {
				// LogUtil.e("extend", "onAnimationEnd");
				sa = null;
				if (oal != null) {
					oal.onEnd();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		startAnimate();
	}


	@Override
	public void unextend() {
		if (sa != null || target.getVisibility() == View.INVISIBLE || target.getVisibility() ==
				View.GONE) {
			return;
		}
		cancelAnimate();

		float toX = hasHorizontalOrientation() ? 0f : 1f;
		float toY = hasVerticalOrientation() ? 0f : 1f;

		sa = new ScaleAnimation(1f, toX, 1f, toY, ScaleAnimation.RELATIVE_TO_SELF, getPivotXValue
				(), ScaleAnimation.RELATIVE_TO_SELF, getPivotYValue());
		sa.setDuration(duration);
		if (interpolator != null) {
			sa.setInterpolator(interpolator);
		}

		sa.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// LogUtil.e("unextend", "onAnimationStart");
				if (oal != null) {
					oal.onStart();
				}
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// LogUtil.e("unextend", "onAnimationEnd");
				target.setVisibility(View.INVISIBLE);
				sa = null;
				if (oal != null) {
					oal.onEnd();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		startAnimate();
	}

	protected void startAnimate() {
		if (sa == null) {
			return;
		}
		target.startAnimation(sa);
	}

	protected void cancelAnimate() {
		if (sa != null) {
			sa.cancel();
			sa = null;
		}
	}

	private float getPivotYValue() {
		float pivotYValue = 0f;
		if (hasBottomOrientation()) {
			if (hasTopOrientation()) {
				pivotYValue = 0.5f;
			} else {
				pivotYValue = 1f;
			}
		}
		return pivotYValue;
	}

	private float getPivotXValue() {
		float pivotXValue = 0f;
		if (hasRightOrientation()) {
			if (hasLeftOrientation()) {
				pivotXValue = 0.5f;
			} else {
				pivotXValue = 1f;
			}
		}
		return pivotXValue;
	}

	public Interpolator getInterpolator() {
		return interpolator;
	}

	public void setInterpolator(Interpolator interpolator) {
		this.interpolator = interpolator;
	}
}
