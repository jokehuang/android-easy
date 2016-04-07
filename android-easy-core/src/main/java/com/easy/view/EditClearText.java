package com.easy.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Joke on 2015/8/22.
 */
public class EditClearText extends EditText implements View.OnTouchListener, View
		.OnFocusChangeListener, TextWatcher {
	private boolean isClearable = true;
	private Drawable clearDrawable;
	private OnTouchListener otl;
	private OnFocusChangeListener ofcl;

	@Override
	public void onFocusChange(View view, boolean b) {
		updateClearDrawable();
		if (ofcl != null) {
			ofcl.onFocusChange(view, b);
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		if (isClearable && motionEvent.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null && motionEvent.getX() > getWidth() -
					getPaddingRight() - getCompoundDrawables()[2].getIntrinsicWidth()) {
				setText("");
				hideClearDrawable();
			}
		}

		if (otl != null) {
			return otl.onTouch(view, motionEvent);
		} else {
			return false;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override
	public void afterTextChanged(Editable editable) {
		updateClearDrawable();
	}

	public boolean isClearable() {
		return isClearable;
	}

	public void setClearable(boolean isClearable) {
		this.isClearable = isClearable;
		updateClearDrawable();
	}

	public Drawable getClearDrawable() {
		return clearDrawable;
	}

	public void setClearDrawable(Drawable clearDrawable) {
		this.clearDrawable = clearDrawable;
		updateClearDrawable();
	}

	public EditClearText(Context context) {
		super(context);
		init();
	}

	public EditClearText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public EditClearText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		clearDrawable = getCompoundDrawables()[2];
		super.setOnTouchListener(this);
		super.setOnFocusChangeListener(this);
		addTextChangedListener(this);
		updateClearDrawable();
	}

	private void updateClearDrawable() {
		if (isClearable) {
			if (isFocused() && getText().length() > 0) {
				showClearDrawable();
			} else {
				hideClearDrawable();
			}
		} else {
			showClearDrawable();
		}
	}

	private void showClearDrawable() {
		super.setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
				clearDrawable, getCompoundDrawables()[3]);
	}

	private void hideClearDrawable() {
		super.setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null,
				getCompoundDrawables()[3]);
	}

	@Override
	public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable
			bottom) {
		super.setCompoundDrawables(left, top, right, bottom);
		clearDrawable = right;
		updateClearDrawable();
	}

	@Override
	public void setOnTouchListener(OnTouchListener l) {
		this.otl = l;
	}

	@Override
	public void setOnFocusChangeListener(OnFocusChangeListener l) {
		this.ofcl = l;
	}
}
