package com.easy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年7月13日
 * @version 1.0.0
 */

@SuppressLint("ClickableViewAccessibility")
public class EasyFragmentPager extends EasyPager<Fragment> {
	protected boolean fragmentDestroyable = true;// 是否允许销毁非邻近的Fragment页面

	/**
	 * 构造器
	 */
	public EasyFragmentPager(Context context) {
		super(context);
		if (context instanceof FragmentActivity) {
			initAdapter(((FragmentActivity) context)
					.getSupportFragmentManager());
		}
	}

	/**
	 * 构造器
	 */
	public EasyFragmentPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (context instanceof FragmentActivity) {
			initAdapter(((FragmentActivity) context)
					.getSupportFragmentManager());
		}
	}

	/**
	 * 默认的Fragment适配器，只在使用Fragment做页面的时候创建
	 */
	class FragmentAdapter extends FragmentPagerAdapter {

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return ls == null ? 0 : ls.size();
		}

		@Override
		public Fragment getItem(int paramInt) {
			return ls.get(paramInt);
		}

		public void destroyItem(ViewGroup container, int position, Object object) {
			if (fragmentDestroyable) {
				// 如果允许销毁Fragment，调用父类方法执行销毁相关的生命周期
				super.destroyItem(container, position, object);
			}
		};
	}

	public void initAdapter(FragmentManager fm) {
		setAdapter(new FragmentAdapter(fm));
	}

	public boolean isFragmentDestroyable() {
		return fragmentDestroyable;
	}

	public void setFragmentDestroyable(boolean fragmentDestroyable) {
		this.fragmentDestroyable = fragmentDestroyable;
	}
}
