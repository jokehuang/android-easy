package com.easy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.easy.R;

/**
 * @description 常用的dialog，包含标题、内容、左右按钮。
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年4月16日
 * @version 1.0.0
 */

public class EasyDialog extends Dialog {
	public EasyDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public EasyDialog(Context context, int theme) {
		super(context, theme);
	}

	public EasyDialog(Context context) {
		super(context);
	}

	public EasyDialog(Context context, int resId, boolean hasFrame) {
		super(context, hasFrame ? android.R.style.Theme_Dialog
				: R.style.easyDialog);
		setContentView(resId);
	}

	private TextView title;
	private TextView content;
	private Button leftBtn;
	private Button rightBtn;

	public void findTitle(int resId) {
		View v = findViewById(resId);
		if (v != null && v instanceof TextView) {
			title = (TextView) v;
		}
	}

	public void findContent(int resId) {
		View v = findViewById(resId);
		if (v != null && v instanceof TextView) {
			content = (TextView) v;
		}
	}

	public void findLeftBtn(int resId) {
		View v = findViewById(resId);
		if (v != null && v instanceof Button) {
			leftBtn = (Button) v;
		}
	}

	public void findRightBtn(int resId) {
		View v = findViewById(resId);
		if (v != null && v instanceof Button) {
			rightBtn = (Button) v;
		}
	}

	public void setTitle(String str) {
		if (title != null)
			title.setText(str);
	}

	public void setTitle(int resId) {
		if (title != null)
			title.setText(resId);
	}

	public void setContent(String str) {
		if (content != null)
			content.setText(str);
	}

	public void setContent(int resId) {
		if (content != null)
			content.setText(resId);
	}

	public void setLeftBtnText(String str) {
		if (leftBtn != null)
			leftBtn.setText(str);
	}

	public void setLeftBtnText(int resId) {
		if (leftBtn != null)
			leftBtn.setText(resId);
	}

	public void setRightBtnText(String str) {
		if (rightBtn != null)
			rightBtn.setText(str);
	}

	public void setRightBtnText(int resId) {
		if (rightBtn != null)
			rightBtn.setText(resId);
	}

	public void setLeftListener(android.view.View.OnClickListener listener) {
		if (leftBtn != null) {
			leftBtn.setOnClickListener(listener);
		}
	}

	public void setRightListener(android.view.View.OnClickListener listener) {
		if (rightBtn != null) {
			rightBtn.setOnClickListener(listener);
		}
	}

}
