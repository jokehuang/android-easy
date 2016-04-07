package com.easy.example;

import android.app.Application;
import android.os.Environment;

import com.easy.example.activity.util.LogUtilActivity;
import com.easy.util.LogUtil;

import org.xutils.x;

import java.io.File;

/**
 * Created by Joke on 2016/3/22.
 */
public class MyApplication extends Application {

	public static String APP_DIR_PATH;

	@Override
	public void onCreate() {
		super.onCreate();
		if (APP_DIR_PATH == null) {
			APP_DIR_PATH = Environment.getExternalStorageDirectory() + File.separator + getString
					(R.string.app_name);
		}
		x.Ext.init(this);
		x.Ext.setDebug(true);
		LogUtil.setDegree(LogUtil.DEGREE_VERBOSE);
		LogUtil.clearDatedLog(LogUtilActivity.LOG_DIR_PATH);
	}
}
