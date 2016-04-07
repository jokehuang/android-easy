package com.easy.animate;

import android.view.View;

/**
 * @author Joke
 * @version 1.0.0
 * @description 展开动画，可以将任意view竖向或横向动态展开和收起。
 * @email 113979462@qq.com
 * @create 2015年5月22日
 */

public abstract class ExtendAnimate {

	public static final int ORIENTATION_LEFT = 0x01;//左
	public static final int ORIENTATION_TOP = 0x10;//上
	public static final int ORIENTATION_RIGHT = 0x02;//右
	public static final int ORIENTATION_BOTTOM = 0x20;//下
	public static final int ORIENTATION_ALL = ORIENTATION_LEFT | ORIENTATION_TOP |
			ORIENTATION_RIGHT | ORIENTATION_BOTTOM;//全部

	protected View target;// 被操作的目标控件
	protected int duration = 500;// 动画持续时间
	protected int orientation;// 动画的方向
	protected OnAnimateListener oal;

	public ExtendAnimate(View target) {
		this(target, 0);
	}

	public ExtendAnimate(View target, int orientation) {
		this.target = target;
		this.orientation = orientation;

		if (target == null) {
			throw new NullPointerException("target can not be null");
		}
	}

	public void reverse() {
		if (target.getVisibility() == View.VISIBLE) {
			unextend();
		} else {
			extend();
		}
	}

	public abstract void extend();

	public abstract void unextend();

	protected abstract void startAnimate();

	protected abstract void cancelAnimate();

	public boolean hasHorizontalOrientation() {
		return (orientation & 0x0f) > 0;
	}

	public boolean hasVerticalOrientation() {
		return (orientation & 0xf0) > 0;
	}

	public boolean hasLeftOrientation() {
		return (orientation & ORIENTATION_LEFT) > 0;
	}

	public boolean hasTopOrientation() {
		return (orientation & ORIENTATION_TOP) > 0;
	}

	public boolean hasRightOrientation() {
		return (orientation & ORIENTATION_RIGHT) > 0;
	}

	public boolean hasBottomOrientation() {
		return (orientation & ORIENTATION_BOTTOM) > 0;
	}

	public void setLeftOrientation(boolean enable) {
		setOrientation(ExtendAnimate.ORIENTATION_LEFT, enable);
	}

	public void setTopOrientation(boolean enable) {
		setOrientation(ExtendAnimate.ORIENTATION_TOP, enable);
	}

	public void setRightOrientation(boolean enable) {
		setOrientation(ExtendAnimate.ORIENTATION_RIGHT, enable);
	}

	public void setBottomOrientation(boolean enable) {
		setOrientation(ExtendAnimate.ORIENTATION_BOTTOM, enable);
	}

	public int getHorizontalOrientation() {
		return orientation & 0x0f;
	}

	public int getVerticalOrientation() {
		return orientation & 0xf0;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}


	public void setOrientation(int orientation, boolean enable) {
		if (enable) {
			this.orientation |= orientation;
		} else {
			this.orientation &= ~orientation;
		}
	}

	public View getTarget() {
		return target;
	}

	public void setTarget(View target) {
		this.target = target;
	}

	public OnAnimateListener getOnAnimateListener() {
		return oal;
	}

	public void setOnAnimateListener(OnAnimateListener oal) {
		this.oal = oal;
	}

	public interface OnAnimateListener {
		void onStart();

		void onEnd();
	}
}
