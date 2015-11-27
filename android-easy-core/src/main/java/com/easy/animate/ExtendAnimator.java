package com.easy.animate;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;

/**
 * @author Joke
 * @version 1.0.0
 * @description 展开动画，可以将任意view竖向或横向动态展开和收起。
 * @email 113979462@qq.com
 * @create 2015年5月22日
 */

public class ExtendAnimator extends ExtendAnimate {
	private ValueAnimator va;// 运行中的属性动画
	private float percent;//当前已经展开的百分比
	private TimeInterpolator interpolator;// 插值器，控制动画的变化快慢规律
	//目标控件原有的尺寸值
	private int extendPaddingLeft;
	private int extendPaddingTop;
	private int extendPaddingRight;
	private int extendPaddingBottom;
	private int measuredWidth = -1;
	private int measuredHeight = -1;

	public ExtendAnimator(View target) {
		super(target);
	}

	public ExtendAnimator(View target, int orientation) {
		super(target, orientation);

		extendPaddingLeft = target.getPaddingLeft();
		extendPaddingTop = target.getPaddingTop();
		extendPaddingRight = target.getPaddingRight();
		extendPaddingBottom = target.getPaddingBottom();

		target.measure(0, 0);
		measuredWidth = target.getMeasuredWidth();
		measuredHeight = target.getMeasuredHeight();

		//		LogUtil.e("init: ", extendPaddingLeft + "," + extendPaddingTop + "," +
		// extendPaddingRight
		//				+ "," + extendPaddingBottom + "," + measuredWidth + "," + measuredHeight);

		if (target.getVisibility() == View.GONE) {
			status = STATUS_UNEXTENDED;
			percent = 0;
			target.setPadding(getUnextendPaddingLeft(), getUnextendPaddingTop(),
					getUnextendPaddingRight(), getUnextendPaddingBottom());
		} else {
			status = STATUS_EXTENDED;
			percent = 1;
		}
	}

	@Override
	public void extend() {
		cancelAnimator();
		va = ValueAnimator.ofFloat(percent, 1).setDuration(duration);
		if (interpolator != null) {
			va.setInterpolator(interpolator);
		}
		va.addListener(new AnimatorListener() {
			boolean isCanceled;

			@Override
			public void onAnimationStart(Animator arg0) {
				// LogUtil.e("extend", "onAnimationStart");
				target.setVisibility(View.VISIBLE);
				status = STATUS_EXTENDING;
				if (oal != null) {
					oal.onStart();
				}
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				// LogUtil.e("extend", "onAnimationRepeat");
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// LogUtil.e("extend", "onAnimationEnd");
				if (!isCanceled) {
					status = STATUS_EXTENDED;
					va = null;
				}
				if (oal != null) {
					oal.onEnd();
				}
			}

			@Override
			public void onAnimationCancel(Animator arg0) {
				// LogUtil.e("extend", "onAnimationCancel");
				isCanceled = true;
			}
		});
		startAnimator();
	}

	@Override
	public void unextend() {
		cancelAnimator();
		va = ValueAnimator.ofFloat(percent, 0).setDuration(duration);
		if (interpolator != null) {
			va.setInterpolator(interpolator);
		}
		va.addListener(new AnimatorListener() {
			boolean isCanceled;

			@Override
			public void onAnimationStart(Animator arg0) {
				// LogUtil.e("unextend", "onAnimationStart");
				status = STATUS_UNEXTENDING;
				if (oal != null) {
					oal.onStart();
				}
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				// LogUtil.e("unextend", "onAnimationRepeat");
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// LogUtil.e("unextend", "onAnimationEnd");
				if (!isCanceled) {
					target.setVisibility(View.GONE);
					status = STATUS_UNEXTENDED;
					va = null;
				}
				if (oal != null) {
					oal.onEnd();
				}
			}

			@Override
			public void onAnimationCancel(Animator arg0) {
				// LogUtil.e("unextend", "onAnimationCancel");
				isCanceled = true;
			}
		});
		startAnimator();
	}

	private void startAnimator() {
		if (va == null) {
			return;
		}
		va.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator valueanimator) {
				percent = (Float) valueanimator.getAnimatedValue();
				int paddingLeft = measureValue(extendPaddingLeft, getUnextendPaddingLeft(),
						percent);
				int paddingTop = measureValue(extendPaddingTop, getUnextendPaddingTop(), percent);
				int paddingRight = measureValue(extendPaddingRight, getUnextendPaddingRight(),
						percent);
				int paddingBottom = measureValue(extendPaddingBottom, getUnextendPaddingBottom(),
						percent);
				target.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
				//				LogUtil.e("ExtendAnimator", "onAnimationUpdate: " + paddingLeft +
				// "," + paddingTop
				//						+ "," +
				//						paddingRight + "," + paddingBottom);
			}
		});
		if (measuredWidth == -1) {
			measuredWidth = target.getWidth();
			measuredHeight = target.getHeight();
		}
		va.start();
	}

	private void cancelAnimator() {
		if (va != null) {
			va.cancel();
			va = null;
		}
	}

	private int measureValue(int extendValue, int unextendValue, float percent) {
		return (int) (unextendValue + (extendValue - unextendValue) * percent);
	}

	private int getUnextendPaddingLeft() {
		if (hasLeftOrientation()) {
			if (hasRightOrientation()) {
				return extendPaddingLeft - measuredWidth / 2;
			} else {
				return extendPaddingLeft - measuredWidth;
			}
		} else {
			return extendPaddingLeft;
		}
	}

	private int getUnextendPaddingTop() {
		if (hasTopOrientation()) {
			if (hasBottomOrientation()) {
				return extendPaddingTop - measuredHeight / 2;
			} else {
				return extendPaddingTop - measuredHeight;
			}
		} else {
			return extendPaddingTop;
		}
	}

	private int getUnextendPaddingRight() {
		if (hasRightOrientation()) {
			if (hasLeftOrientation()) {
				return extendPaddingRight - measuredWidth / 2;
			} else {
				return extendPaddingRight - measuredWidth;
			}
		} else {
			return extendPaddingRight;
		}
	}

	private int getUnextendPaddingBottom() {
		if (hasBottomOrientation()) {
			if (hasTopOrientation()) {
				return extendPaddingBottom - measuredHeight / 2;
			} else {
				return extendPaddingBottom - measuredHeight;
			}
		} else {
			return extendPaddingBottom;
		}
	}

	public TimeInterpolator getInterpolator() {
		return interpolator;
	}

	public void setInterpolator(TimeInterpolator interpolator) {
		this.interpolator = interpolator;
	}

}
