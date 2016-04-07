package com.easy.example.activity.util;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.easy.activity.EasyActivity;
import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.AppUtil;
import com.easy.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.activity_apputil)
public class AppUtilActivity extends BaseActivity {

	@ViewInject(R.id.tb_toast)
	private ToggleButton tb_toast;
	private Runnable toastRunnable;

	@Event(value = R.id.tb_toast, type = CompoundButton.OnCheckedChangeListener.class)
	private void onCheckedChangeToast(CompoundButton cb, boolean checked) {
		if (toastRunnable == null) {
			toastRunnable = new Runnable() {
				@Override
				public void run() {
					ToastUtil.show(self, "isForeground: " + AppUtil.isForeground(self), self
							.getClass().getSimpleName());
					tb_toast.postDelayed(this, 500);
				}
			};
		}
		if (checked) {
			toastRunnable.run();
		} else {
			tb_toast.removeCallbacks(toastRunnable);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		tb_toast.removeCallbacks(toastRunnable);
	}
}
