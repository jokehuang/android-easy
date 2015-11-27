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

		if (target.getVisibility() == View.GONE) {
			status = STATUS_UNEXTENDED;
		} else {
			status = STATUS_EXTENDED;
		}
	}

	@Override
	public void extend() {
		if (status == STATUS_EXTENDING || status == STATUS_EXTENDED) {
			return;
		}
		cancelAnimator();

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
				status = STATUS_EXTENDING;
				if (oal != null) {
					oal.onStart();
				}
			}


			@Override
			public void onAnimationEnd(Animation animation) {
				// LogUtil.e("extend", "onAnimationEnd");
				status = STATUS_EXTENDED;
				sa = null;
				if (oal != null) {
					oal.onEnd();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		startAnimator();
	}


	@Override
	public void unextend() {
		if (status == STATUS_UNEXTENDING || status == STATUS_UNEXTENDED) {
			return;
		}
		cancelAnimator();

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
				status = STATUS_UNEXTENDING;
				if (oal != null) {
					oal.onStart();
				}
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// LogUtil.e("unextend", "onAnimationEnd");
				target.setVisibility(View.GONE);
				status = STATUS_UNEXTENDED;
				sa = null;
				if (oal != null) {
					oal.onEnd();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		startAnimator();
	}

	private void startAnimator() {
		if (sa == null) {
			return;
		}
		target.startAnimation(sa);
	}

	private void cancelAnimator() {
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
