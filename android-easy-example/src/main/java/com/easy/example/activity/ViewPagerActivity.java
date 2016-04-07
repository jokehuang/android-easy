package com.easy.example.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.easy.activity.EasyActivity;
import com.easy.example.R;
import com.easy.util.ToastUtil;
import com.easy.view.EasyViewPager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_view_pager)
public class ViewPagerActivity extends BaseActivity {

	@ViewInject(R.id.vp)
	private EasyViewPager vp;
	@ViewInject(R.id.tv_interval)
	private TextView tv_interval;
	@ViewInject(R.id.rg)
	private RadioGroup rg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView iv = null;
		iv = new ImageView(this);
		iv.setImageResource(R.drawable.ab1);
		vp.addPage(iv);
		iv = new ImageView(this);
		iv.setImageResource(R.drawable.ab2);
		vp.addPage(iv);
		iv = new ImageView(this);
		iv.setImageResource(R.drawable.ab3);
		vp.addPage(iv);
		iv = new ImageView(this);
		iv.setImageResource(R.drawable.ab4);
		vp.addPage(iv);
		vp.loop();
		vp.setAutoScroll(true);
		vp.setRadioGroup(rg);
	}

	@Event(value = R.id.vp, type = EasyViewPager.OnItemClickListener.class)
	private void onItemClick(View v, int position) {
		ToastUtil.show(self, "position: " + position);
	}

	@Event(value = R.id.tb_scrollable, type = CompoundButton.OnCheckedChangeListener.class)
	private void onCheckedChangeScrollable(CompoundButton cb, boolean checked) {
		vp.setScrollable(checked);
	}

	@Event(value = R.id.tb_auto_scroll, type = CompoundButton.OnCheckedChangeListener.class)
	private void onCheckedChangeAutoScroll(CompoundButton cb, boolean checked) {
		vp.setAutoScroll(checked);
	}

	@Event(value = R.id.sb, type = SeekBar.OnSeekBarChangeListener.class, method =
			"onProgressChanged")
	private void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		int interval = 100 + progress * 100;
		tv_interval.setText(interval + "ms");
		vp.setAutoScrollInterval(interval);
	}
}
