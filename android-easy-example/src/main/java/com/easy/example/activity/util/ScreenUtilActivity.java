package com.easy.example.activity.util;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.ScreenUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_screen_util)
public class ScreenUtilActivity extends BaseActivity {

    @ViewInject(R.id.tv_type)
    private TextView tv_type;
    @ViewInject(R.id.tv_width)
    private TextView tv_width;
    @ViewInject(R.id.tv_height)
    private TextView tv_height;
    @ViewInject(R.id.tv_rotation)
    private TextView tv_rotation;
    @ViewInject(R.id.tv_orientation)
    private TextView tv_orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        update();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        update();
    }

    private void update(){
        ScreenUtil.ScreenInfo screenInfo = ScreenUtil.getInfo(this);
        tv_type.setText(screenInfo.getType() == ScreenUtil.ScreenInfo.TYPE_PHONE ? "手机" : "平板");
        tv_width.setText(screenInfo.getWidth() + "");
        tv_height.setText(screenInfo.getHeight() + "");
        tv_rotation.setText(screenInfo.getRotation() + "");
        tv_orientation.setText(screenInfo.getOrientation() == ScreenUtil.ScreenInfo.ORIENTATION_PORTRAIT ? "竖屏" : "横屏");
    }
}
