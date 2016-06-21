package com.easy.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author Joke Huang
 * @version 1.0.0
 * @createDate 2014年6月18日
 */

public class ScreenUtil {

    public static class ScreenInfo {
        public static final int TYPE_PHONE = 1;
        public static final int TYPE_PAD = 2;
        public static final int ORIENTATION_PORTRAIT = 1;
        public static final int ORIENTATION_LANDSCAPE = 2;

        private int type;
        private int height;
        private int width;
        private int longSide;
        private int shortSide;
        private int rotation;
        private int orientation;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getRotation() {
            return rotation;
        }

        public void setRotation(int rotation) {
            this.rotation = rotation;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getLongSide() {
            return longSide;
        }

        public void setLongSide(int longSide) {
            this.longSide = longSide;
        }

        public int getShortSide() {
            return shortSide;
        }

        public void setShortSide(int shortSide) {
            this.shortSide = shortSide;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }
    }

    public static ScreenInfo getInfo(Context context) {
        ScreenInfo info = new ScreenInfo();

        WindowManager wm = SystemUtil.getWindowManager(context);
        Display d = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        d.getMetrics(outMetrics);

        info.height = outMetrics.heightPixels;
        info.width = outMetrics.widthPixels;
        info.longSide = info.height > info.width ? info.height : info.width;
        info.shortSide = info.height < info.width ? info.height : info.width;
        info.rotation = d.getRotation();

        boolean isDefaultRotation = info.rotation % 2 == 0;
        boolean isPortrait = info.height > info.width;
        info.orientation = isPortrait ? ScreenInfo.ORIENTATION_PORTRAIT : ScreenInfo.ORIENTATION_LANDSCAPE;

        if (isDefaultRotation ^ isPortrait) {
            info.type = ScreenInfo.TYPE_PAD;
        } else {
            info.type = ScreenInfo.TYPE_PHONE;
        }

        return info;

        // Log.v("ScreenUtil", "Rotation: " + d.getRotation());
        // Log.v("ScreenUtil", "screenHeight: " + screenHeight);
        // Log.v("ScreenUtil", "screenWidth: " + screenWidth);
        // Log.v("ScreenUtil", "screenRelativeHeight: " +
        // screenRelativeHeight);
        // Log.v("ScreenUtil", "screenRelativeWidth: " + screenRelativeWidth);
        // Log.v("ScreenUtil", "scale: " + scale);
        // Log.v("ScreenUtil", "type: " + (type == TYPE_PHONE ? "PHONE" :
        // "PAD"));

    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getType(Context context) {
        return getInfo(context).type;
    }

    public static int getHeight(Context context) {
        return getInfo(context).height;
    }

    public static int getWidth(Context context) {
        return getInfo(context).width;
    }

    public static int getLongSide(Context context) {
        return getInfo(context).longSide;
    }

    public static int getShortSide(Context context) {
        return getInfo(context).shortSide;
    }

    public static int getRotation(Context context) {
        return getInfo(context).rotation;
    }
}
