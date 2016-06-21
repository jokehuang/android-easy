package com.easy.example.activity.util;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.AppUtil;
import com.easy.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_app_util)
public class AppUtilActivity extends BaseActivity {
    @ViewInject(R.id.tv_version_name)
    private TextView tv_version_name;
    @ViewInject(R.id.tv_version_code)
    private TextView tv_version_code;
    @ViewInject(R.id.tb_toast)
    private ToggleButton tb_toast;
    private Runnable toastRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_version_name.setText(AppUtil.getVersionName(this));
        tv_version_code.setText(AppUtil.getVersionCode(this) + "");
    }

    @Event(value = R.id.tb_toast, type = CompoundButton.OnCheckedChangeListener.class)
    private void onCheckedChangeToast(CompoundButton cb, boolean checked) {
        if (toastRunnable == null) {
            toastRunnable = new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(self, "isForeground: " + AppUtil.isForeground(self), self.getClass().getSimpleName());
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
