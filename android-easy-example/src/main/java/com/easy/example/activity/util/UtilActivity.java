package com.easy.example.activity.util;

import android.view.View;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.example.activity.util.AppUtilActivity;
import com.easy.example.activity.util.BadgeUtilActivity;
import com.easy.example.activity.util.BitmapUtilActivity;
import com.easy.example.activity.util.DataUtilActivity;
import com.easy.example.activity.util.FileUtilActivity;
import com.easy.example.activity.util.IntentUtilActivity;
import com.easy.example.activity.util.LogUtilActivity;
import com.easy.example.activity.util.NetworkUtilActivity;
import com.easy.example.activity.util.ScreenUtilActivity;
import com.easy.example.activity.util.VersionUtilActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_util)
public class UtilActivity extends BaseActivity {

    @Event(value = {R.id.btn_app_util, R.id.btn_badge_util, R.id.btn_bitmap_util, R.id.btn_data_util, R.id
            .btn_dialog_util, R.id.btn_empty_util, R.id.btn_file_util, R.id.btn_intent_util, R.id.btn_keyboard_util,
            R.id.btn_log_util, R.id.btn_network_util, R.id.btn_reflect_util, R.id.btn_screen_util, R.id
            .btn_system_util, R.id.btn_time_util, R.id.btn_toast_util, R.id.btn_version_util},
            type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_app_util:
                startActivity(AppUtilActivity.class);
                break;
            case R.id.btn_badge_util:
                startActivity(BadgeUtilActivity.class);
                break;
            case R.id.btn_bitmap_util:
                startActivity(BitmapUtilActivity.class);
                break;
            case R.id.btn_data_util:
                startActivity(DataUtilActivity.class);
                break;
            case R.id.btn_dialog_util:
                break;
            case R.id.btn_empty_util:
                break;
            case R.id.btn_file_util:
                startActivity(FileUtilActivity.class);
                break;
            case R.id.btn_intent_util:
                startActivity(IntentUtilActivity.class);
                break;
            case R.id.btn_keyboard_util:
                break;
            case R.id.btn_log_util:
                startActivity(LogUtilActivity.class);
                break;
            case R.id.btn_network_util:
                startActivity(NetworkUtilActivity.class);
                break;
            case R.id.btn_reflect_util:
                break;
            case R.id.btn_screen_util:
                startActivity(ScreenUtilActivity.class);
                break;
            case R.id.btn_system_util:
                break;
            case R.id.btn_time_util:
                break;
            case R.id.btn_toast_util:
                break;
            case R.id.btn_version_util:
                startActivity(VersionUtilActivity.class);
                break;
        }
    }
}
