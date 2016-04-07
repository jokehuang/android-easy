package com.easy.example.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.easy.activity.EasyActivity;
import com.easy.animate.ExtendAnimate;
import com.easy.animate.ExtendAnimation;
import com.easy.animate.ExtendAnimator;
import com.easy.example.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_animate)
public class AnimateActivity extends BaseActivity {

	@ViewInject(R.id.target)
	private View target;
	private ExtendAnimation eAnimation;
	private ExtendAnimator eAnimator;
	private ExtendAnimate currentAnimate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		eAnimation = new ExtendAnimation(target, ExtendAnimate.ORIENTATION_ALL);
		eAnimator = new ExtendAnimator(target, ExtendAnimate.ORIENTATION_ALL);
		currentAnimate = eAnimation;
	}

	@Event(value = R.id.rg_animate, type = RadioGroup.OnCheckedChangeListener.class)
	private void onCheckedChanged(RadioGroup group, int checkedId) {
		currentAnimate.extend();
		switch (checkedId) {
			case R.id.rb_animation:
				currentAnimate = eAnimation;
				break;
			case R.id.rb_animator:
				currentAnimate = eAnimator;
				break;
		}
	}

	@Event(value = {R.id.cb_left, R.id.cb_top, R.id.cb_right, R.id.cb_bottom}, type =
			CompoundButton.OnCheckedChangeListener.class)
	private void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
			case R.id.cb_left:
				eAnimation.setLeftOrientation(isChecked);
				eAnimator.setLeftOrientation(isChecked);
				break;
			case R.id.cb_top:
				eAnimation.setTopOrientation(isChecked);
				eAnimator.setTopOrientation(isChecked);
				break;
			case R.id.cb_right:
				eAnimation.setRightOrientation(isChecked);
				eAnimator.setRightOrientation(isChecked);
				break;
			case R.id.cb_bottom:
				eAnimation.setBottomOrientation(isChecked);
				eAnimator.setBottomOrientation(isChecked);
				break;
		}
	}

	@Event(value = R.id.btn_reverse, type = View.OnClickListener.class)
	private void onClickReverse(View v) {
		currentAnimate.reverse();
	}

	@Event(value = R.id.btn_extend, type = View.OnClickListener.class)
	private void onClickExtend(View v) {
		currentAnimate.extend();
	}

	@Event(value = R.id.btn_unextend, type = View.OnClickListener.class)
	private void onClickUnextend(View v) {
		currentAnimate.unextend();
	}
}
