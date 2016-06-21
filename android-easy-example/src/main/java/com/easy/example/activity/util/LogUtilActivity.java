package com.easy.example.activity.util;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easy.example.MyApplication;
import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.EmptyUtil;
import com.easy.util.KeyboardUtil;
import com.easy.util.LogUtil;
import com.easy.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

@ContentView(R.layout.activity_log_util)
public class LogUtilActivity extends BaseActivity {

    public static final String LOG_DIR_PATH = MyApplication.APP_DIR_PATH + File.separator + "log";

    @ViewInject(R.id.et)
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KeyboardUtil.showDelayed(this, et, 300);
    }

    @Event(value = R.id.btn_log2file, type = View.OnClickListener.class)
    private void onClick(View view) {
        String msg = et.getText().toString();
        if (EmptyUtil.isEmpty(msg)) {
            ToastUtil.show(this, "请输入Log内容");
            return;
        }
        LogUtil.log2File(LOG_DIR_PATH, msg);
        ToastUtil.show(this, "已经将log打印到: " + LOG_DIR_PATH);
    }
}
