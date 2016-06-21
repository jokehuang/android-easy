package com.easy.helper;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

/**
 * @description 滑动控件嵌套时，使得内层滑动控件和外层滑动控件分别能滑动。 注意：会覆盖控件的onTouchLinstener。
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年3月17日
 * @version 1.0.0
 */

public class ScrollController implements OnTouchListener {
	private ScrollController outer;
	private View target;
	private OnTouchListener customTouchListener;

	public ScrollController(View v) {
		target = v;
		target.setOnTouchListener(this);
	}

	public ScrollController getOuter() {
		return outer;
	}

	public void setOuter(ScrollController outer) {
		this.outer = outer;
	}

	public View getTarget() {
		return target;
	}

	public void setTarget(View target) {
		this.target = target;
	}

	public OnTouchListener getCustomTouchListener() {
		return customTouchListener;
	}

	public void setCustomTouchListener(OnTouchListener customTouchListener) {
		this.customTouchListener = customTouchListener;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		boolean result = false;
		if (customTouchListener != null) {
			result = customTouchListener.onTouch(v, event);
		}
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return result;
		}

		disallowOuterTouch();

		if (v instanceof ViewGroup) {
			((ViewGroup) v).requestDisallowInterceptTouchEvent(false);
		}

		return result;
	}

	private void disallowOuterTouch() {
		if(outer!=null){
			outer.disallowOuterTouch();
			if(outer.target instanceof ViewGroup){
				((ViewGroup) outer.target).requestDisallowInterceptTouchEvent(true);
			}
		}
	}

}
