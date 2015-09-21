package com.easy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.easy.util.EmptyUtil;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年7月13日
 */

@SuppressLint("ClickableViewAccessibility")
public class EasyViewPager extends EasyPager<View> implements View.OnClickListener {
	private OnItemClickListener onItemClickListener;

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

	@Override
	public void onClick(View v) {
		if (onItemClickListener != null) {
			onItemClickListener.onItemClick(v, ls.indexOf(v));
		}
	}

	/**
	 * 默然的View适配器，只在使用View做页面的时候创建
	 */

	class ViewAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return isLoop ? Integer.MAX_VALUE : (ls == null ? 0 : ls.size());
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (EmptyUtil.isEmpty(ls)) {
				return null;
			}
			if (isLoop) {
				position = toUnLoopPosition(position);
			}
			View v = ls.get(position);
			if (isLoop) {
				ViewParent vp = v.getParent();
				if (vp != null) {
					ViewGroup vg = (ViewGroup) vp;
					vg.removeView(v);
				}
			}
			container.addView(v);
			if (onItemClickListener != null) {
				v.setOnClickListener(EasyViewPager.this);
			}
			return v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			if (!isLoop) container.removeView(ls.get(position));
		}
	}

	public void initAdapter() {
		setAdapter(new ViewAdapter());
	}

	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public static interface OnItemClickListener {
		void onItemClick(View v, int position);
	}
}
