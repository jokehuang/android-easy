package com.easy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.easy.manager.EasyActivityManager;
import com.easy.util.ImmersionUtil;
import com.easy.util.KeyboardUtil;
import com.easy.util.LogUtil;
import com.easy.util.ToastUtil;

/**
 * @author Joke Huang
 * @version 1.1.0
 * @description 1、连续双击退出应用 2、懒人专用 3、打印生命周期log
 * @createDate 2014年6月30日
 */

public class EasyActivity extends FragmentActivity {
    // 双击退出应用的有效间隔时间
    private static final int BACK_PRESSED_TIME = 2000;
    //是否沉浸式
    private boolean isImmersion;
    //沉浸颜色
    private int statusColor;
    //    private int navigationColor;
    // log使用的tag
    protected final String tag = this.getClass().getSimpleName();
    // 指向activity自己，当内部类调用activity时，不用写“类名.this”，供懒人使用
    protected final EasyActivity self = this;
    // 最后一次按下后退键的时间
    private long lastBackPressedTime;
    // 是否打印生命周期相关的log
    private boolean isLogLife;
    // 是否点击外部时自动取消输入框焦点
    private boolean isAutoClearFocus = true;

    @Override
    protected void onNewIntent(Intent intent) {
        if (isLogLife) log("onNewIntent");
        setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isLogLife) log("onCreate");
        super.onCreate(savedInstanceState);
        EasyActivityManager.getInstance().add(this);
    }

    @Override
    protected void onStart() {
        if (isLogLife) log("onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        if (isLogLife) log("onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        if (isLogLife) log("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (isLogLife) log("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (isLogLife) log("onStop");
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isLogLife) log("onActivityResult: " + requestCode + "/" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (isLogLife) log("onDestroy");
        super.onDestroy();
        EasyActivityManager.getInstance().remove(this);
    }

    /**
     * 短时间内连续调用两次将推出app
     *
     * @param exitTipsId
     */
    protected void exit(int exitTipsId) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackPressedTime > BACK_PRESSED_TIME) {
            lastBackPressedTime = currentTime;
            ToastUtil.show(this, exitTipsId);
        } else {
            onExit();
        }
    }

    protected void onExit(){
        if (isLogLife) log("onExit");
        EasyActivityManager.getInstance().finishAll();
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoClearFocus && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
                if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                    // 点击EditText的事件，忽略它。
                } else {
                    KeyboardUtil.hide(this, v);
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (isImmersion) {
            ImmersionUtil.setStatusBarColor(this, statusColor);
            //            ImmersionUtil.setNavigationBarColor(this, navigationColor);
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        if (isImmersion) {
            ImmersionUtil.setStatusBarColor(this, statusColor);
            //            ImmersionUtil.setNavigationBarColor(this, navigationColor);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        if (isImmersion) {
            ImmersionUtil.setStatusBarColor(this, statusColor);
            //            ImmersionUtil.setNavigationBarColor(this, navigationColor);
        }
    }

    public View getContentView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    public void startActivity(Class<? extends Activity> activityClass) {
        startActivity(activityClass, null);
    }

    public void startActivity(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void replaceFragment(int contentId, Fragment fragment) {
        replaceFragment(contentId, fragment, false);
    }

    public void replaceFragment(int contentId, Fragment fragment, boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager().beginTransaction().replace(contentId, fragment).addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(contentId, fragment).commit();
        }
    }

    public void log(Object obj) {
        LogUtil.v(tag, obj);
    }

    public boolean isLogLife() {
        return isLogLife;
    }

    public void setIsLogLife(boolean isLogLife) {
        this.isLogLife = isLogLife;
    }

    public boolean isAutoClearFocus() {
        return isAutoClearFocus;
    }

    public void setIsAutoClearFocus(boolean isAutoClearFocus) {
        this.isAutoClearFocus = isAutoClearFocus;
    }

    public void setImmersion(int statusColor) {
        isImmersion = true;
        this.statusColor = statusColor;
        //        this.navigationColor = navigationColor;
        ImmersionUtil.setStatusBarColor(this, statusColor);
        //        ImmersionUtil.setNavigationBarColor(this, navigationColor);
    }
}
