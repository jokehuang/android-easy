package com.easy.animate;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.ViewGroup;

import com.easy.util.LogUtil;

import java.security.InvalidParameterException;
import java.security.spec.InvalidParameterSpecException;

/**
 * @author Joke
 * @version 1.0.0
 * @description 展开动画，可以将任意view竖向或横向动态展开和收起。
 * 由于是通过修改padding达到动画效果，所以要求layout_width或者layout_height必须为wrap_content，不然动画将没有效果
 * @email 113979462@qq.com
 * @create 2015年5月22日
 */

public class ExtendAnimator extends ExtendAnimate {
	private ValueAnimator va;// 运行中的属性动画
	private float percent;//当前已经展开的百分比
	private TimeInterpolator interpolator;// 插值器，控制动画的变化快慢规律
	//目标控件原有的尺寸值
	private int originalPaddingLeft;
	private int originalPaddingTop;
	private int originalPaddingRight;
	private int originalPaddingBottom;
	private int originalWidth;
	private int originalHeight;

	public ExtendAnimator(View target) {
		super(target);
		init();
	}

	public ExtendAnimator(View target, int orientation) {
		super(target, orientation);
		init();
	}

	private void init() {
		originalPaddingLeft = target.getPaddingLeft();
		originalPaddingTop = target.getPaddingTop();
		originalPaddingRight = target.getPaddingRight();
		originalPaddingBottom = target.getPaddingBottom();
	}

	@Override
	public void extend() {
		if (va == null) {
			if (target.getVisibility() == View.VISIBLE) {
				return;
			} else {
				percent = 0;
			}
		}
		cancelAnimate();
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
		startAnimate();
	}

	@Override
	public void unextend() {
		if (va == null) {
			if (target.getVisibility() == View.VISIBLE) {
				percent = 1;
			} else {
				return;
			}
		}
		cancelAnimate();
		va = ValueAnimator.ofFloat(percent, 0).setDuration(duration);
		if (interpolator != null) {
			va.setInterpolator(interpolator);
		}
		va.addListener(new AnimatorListener() {
			boolean isCanceled;

			@Override
			public void onAnimationStart(Animator arg0) {
				// LogUtil.e("unextend", "onAnimationStart");
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
					target.setVisibility(View.INVISIBLE);
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
		startAnimate();
	}

	protected void startAnimate() {
		if (va == null) {
			return;
		}
		va.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator valueanimator) {
				percent = (Float) valueanimator.getAnimatedValue();
				int paddingLeft = measureValue(originalPaddingLeft, getUnextendPaddingLeft(),
						percent);
				int paddingTop = measureValue(originalPaddingTop, getUnextendPaddingTop(),
						percent);
				int paddingRight = measureValue(originalPaddingRight, getUnextendPaddingRight(),
						percent);
				int paddingBottom = measureValue(originalPaddingBottom, getUnextendPaddingBottom()
						, percent);

				//				LogUtil.e("ExtendAnimator", "onAnimationUpdate: " + paddingLeft +
				//						"," + paddingTop + "," +
				//						paddingRight + "," + paddingBottom);
				target.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
			}
		});
		if (originalWidth <= 0) {
			originalWidth = target.getWidth();
			originalHeight = target.getHeight();
			//			LogUtil.e("init: ", target.getWidth() + "," + target.getHeight());
		}
		va.start();
	}

	protected void cancelAnimate() {
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
				return originalPaddingLeft - originalWidth / 2;
			} else {
				return originalPaddingLeft - originalWidth;
			}
		} else {
			return originalPaddingLeft;
		}
	}

	private int getUnextendPaddingTop() {
		if (hasTopOrientation()) {
			if (hasBottomOrientation()) {
				return originalPaddingTop - originalHeight / 2;
			} else {
				return originalPaddingTop - originalHeight;
			}
		} else {
			return originalPaddingTop;
		}
	}

	private int getUnextendPaddingRight() {
		if (hasRightOrientation()) {
			if (hasLeftOrientation()) {
				return originalPaddingRight - originalWidth / 2;
			} else {
				return originalPaddingRight - originalWidth;
			}
		} else {
			return originalPaddingRight;
		}
	}

	private int getUnextendPaddingBottom() {
		if (hasBottomOrientation()) {
			if (hasTopOrientation()) {
				return originalPaddingBottom - originalHeight / 2;
			} else {
				return originalPaddingBottom - originalHeight;
			}
		} else {
			return originalPaddingBottom;
		}
	}

	public TimeInterpolator getInterpolator() {
		return interpolator;
	}

	public void setInterpolator(TimeInterpolator interpolator) {
		this.interpolator = interpolator;
	}

}
