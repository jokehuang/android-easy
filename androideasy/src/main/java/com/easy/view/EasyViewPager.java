package com.easy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年7月13日
 * @version 1.0.0
 */

@SuppressLint("ClickableViewAccessibility")
public class EasyViewPager extends EasyPager<View> {
	/**
	 * 构造器
	 */
	public EasyViewPager(Context context) {
		super(context);
		initAdapter();
	}

	/**
	 * 构造器
	 */
	public EasyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAdapter();
	}

	/**
	 * 默然的View适配器，只在使用View做页面的时候创建
	 */

	class ViewAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return ls == null ? 0 : ls.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(ls.get(position));
			return ls.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(ls.get(position));
		}
	}

	public void initAdapter() {
		setAdapter(new ViewAdapter());
	}
}
