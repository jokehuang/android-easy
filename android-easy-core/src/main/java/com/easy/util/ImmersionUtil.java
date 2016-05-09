package com.easy.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年6月8日
 */

public class ImmersionUtil {
    private ImmersionUtil() {
    }

    /**
     * 设置状态栏颜色
     *
     * @param act
     * @param color
     */
    public static void setStatusBarColor(Activity act, int color) {
        //版本不足
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;

        //未初始化view
        ViewGroup rootView = (ViewGroup) act.findViewById(android.R.id.content);
        if (rootView == null) return;

        //未设置contentView
        View contentView = rootView.getChildAt(0);
        if (contentView == null) return;

        //状态栏高度小于0
        int height = getHeight(act, "status_bar_height");
        if (height <= 0) return;

        //设置沉浸式
        act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //避免状态栏覆盖内容，将内容位置下移
        ViewGroup.MarginLayoutParams mpl = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        mpl.topMargin = height;
        contentView.setLayoutParams(mpl);

        //添加背景布局
        RelativeLayout backGroundLayout = addBackGroundLayout(act, rootView);

        //添加新的背景色
        View v = new View(act);
        v.setBackgroundColor(color);
        backGroundLayout.addView(v, ViewGroup.LayoutParams.MATCH_PARENT, height);
    }

    /**
     * 设置导航栏颜色
     *
     * @param act
     * @param color
     */
    public static void setNavigationBarColor(Activity act, int color) {
        //版本不足
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;

        //未初始化view
        ViewGroup rootView = (ViewGroup) act.findViewById(android.R.id.content);
        if (rootView == null) return;

        //未设置contentView
        View contentView = rootView.getChildAt(0);
        if (contentView == null) return;

        //导航栏高度小于0
        int height = getHeight(act, "navigation_bar_height");
        if (height <= 0) return;

        //设置沉浸式
        act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //避免导航栏覆盖内容，将内容位置上移
        ViewGroup.MarginLayoutParams mpl = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        mpl.bottomMargin = height;
        contentView.setLayoutParams(mpl);

        //添加背景布局
        RelativeLayout backGroundLayout = addBackGroundLayout(act, rootView);

        //添加新的背景色
        View v = new View(act);
        v.setBackgroundColor(color);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        backGroundLayout.addView(v, lp);
    }

    private static RelativeLayout addBackGroundLayout(Context context, ViewGroup rootView) {
        RelativeLayout backGroundLayout = (RelativeLayout) rootView.findViewById(android.R.id.background);
        if (backGroundLayout == null) {
            backGroundLayout = new RelativeLayout(context);
            rootView.addView(backGroundLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .MATCH_PARENT);
        }
        return backGroundLayout;
    }

    /**
     * 获取android控件高度
     *
     * @param context
     * @return
     */
    public static int getHeight(Context context, String name) {
        int resourceId = context.getResources().getIdentifier(name, "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
