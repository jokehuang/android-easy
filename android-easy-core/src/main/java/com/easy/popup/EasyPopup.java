package com.easy.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年4月16日
 * @version 1.0.0
 */

public class EasyPopup extends PopupWindow {
	protected Context context;

	public EasyPopup() {
		super();
		init();
	}

	public EasyPopup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
		this.context = context;
	}

	public EasyPopup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		this.context = context;
	}

	public EasyPopup(Context context) {
		super(context);
		init();
		this.context = context;
	}

	public EasyPopup(Context context, int resId) {
		this(context, resId, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}

	public EasyPopup(Context context, int resId, int width, int height) {
		super(context);
		View v = LayoutInflater.from(context).inflate(resId, null);
		setContentView(v);
		setWidth(width);
		setHeight(height);
		init();
		this.context = context;
	}

	public EasyPopup(int width, int height) {
		super(width, height);
		init();
	}

	public EasyPopup(View contentView, int width, int height, boolean focusable) {
		super(contentView, width, height, focusable);
		setOutsideDismissable(true);
	}

	public EasyPopup(View contentView, int width, int height) {
		super(contentView, width, height);
		init();
	}

	public EasyPopup(View contentView) {
		super(contentView);
		init();
	}

	private void init() {
		setFocusable(true);
		setOutsideDismissable(true);
	}

	@SuppressWarnings("deprecation")
	public void setOutsideDismissable(boolean dismissable) {
		if (dismissable) {
			setOutsideTouchable(true);
			setBackgroundDrawable(new BitmapDrawable());
		} else {
			setBackgroundDrawable(null);
		}
	}

	public View findViewById(int id) {
		return getContentView().findViewById(id);
	}
}
