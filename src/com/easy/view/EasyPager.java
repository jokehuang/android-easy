package com.easy.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @Description 1、可设置单选按钮与页面绑定 2、自带简易的adapter 3、更多扩展功能
 * @author Joke Huang
 * @createDate 2015年1月7日
 * @version 1.1.0
 */

abstract class EasyPager<T> extends ViewPager {

	protected boolean isScrollable = true;// 是否允许滑动切换页面
	protected RadioGroup rg;// 对应页面的单选按钮组
	protected List<T> ls;// 页面
	protected OnPageChangeListener opclCustom;// 自定义的页面切换监听器

	/**
	 * 默认页面切换监听器
	 */
	private OnPageChangeListener opcl = new OnPageChangeListener() {
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// 回调自定义的监听器
			if (opclCustom != null) {
				opclCustom.onPageScrollStateChanged(arg0);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// 回调自定义的监听器
			if (opclCustom != null) {
				opclCustom.onPageScrolled(arg0, arg1, arg2);
			}
		}

		@Override
		public void onPageSelected(int arg0) {
			// 切换页面后选中对应的按钮
			if (rg != null && arg0 < rg.getChildCount()) {
				int id = rg.getChildAt(arg0).getId();
				if (id != rg.getCheckedRadioButtonId()) {
					rg.check(id);
				}
			}

			// 回调自定义的监听器
			if (opclCustom != null) {
				opclCustom.onPageSelected(arg0);
			}
		}
	};

	/**
	 * 点击单选按钮组的监听器
	 */
	private OnCheckedChangeListener occl = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// 点击按钮后切换到对应页面
			for (int i = 0; i < rg.getChildCount(); i++) {
				if (rg.getChildAt(i).getId() == checkedId
						&& i < getAdapter().getCount() && i != getCurrentItem()) {
					setCurrentItem(i, true);
					return;
				}
			}
		}
	};

	/**
	 * 构造器
	 */
	public EasyPager(Context context) {
		super(context);
		super.setOnPageChangeListener(opcl);
	}

	/**
	 * 构造器
	 */
	public EasyPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setOnPageChangeListener(opcl);
	}

	/**
	 * 设置页面对应的单选按钮
	 */
	public void setRadioGroup(RadioGroup rg) {
		if (this.rg != null) {
			this.rg.setOnCheckedChangeListener(null);
		}
		this.rg = rg;
		rg.setOnCheckedChangeListener(occl);
	}

	/**
	 * 设置页面内容
	 */
	public void setPages(List<T> ls) {
		this.ls = ls;
		notifyDataSetChanged();
	}

	/**
	 * 添加页面
	 */
	public void addPages(List<T> pages) {
		if (ls == null) {
			ls = new ArrayList<T>();
		}
		ls.addAll(pages);
		notifyDataSetChanged();
	}

	/**
	 * 添加页面
	 */
	public void addPage(T page) {
		if (ls == null) {
			ls = new ArrayList<T>();
		}
		ls.add(page);
		notifyDataSetChanged();
	}

	/**
	 * 刷新UI
	 */
	public void notifyDataSetChanged() {
		if (getAdapter() != null) {
			getAdapter().notifyDataSetChanged();
		}
	}

	/**
	 * 获取当前页面
	 */
	public T getCurrentPage() {
		return ls == null ? null : ls.get(getCurrentItem());
	}

	/**
	 * 获取对应下标的页面
	 */
	public T getPage(int index) {
		try {
			return ls.get(index);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取当前选中的按钮Id
	 */
	public int getCheckedId() {
		return rg == null ? -1 : rg.getCheckedRadioButtonId();
	}

	/**
	 * 设置自定义的页面切换监听器
	 */
	@Override
	public void setOnPageChangeListener(OnPageChangeListener opclCustom) {
		this.opclCustom = opclCustom;
	}

	/**
	 * 控制是否允许滚动切换页面
	 * 
	 * @param ev
	 * @return
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isScrollable == false) {
			return false;
		} else {
			return super.onTouchEvent(ev);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (isScrollable == false) {
			return false;
		} else {
			return super.onInterceptTouchEvent(ev);
		}
	}

	public boolean isScrollable() {
		return isScrollable;
	}

	public void setScrollable(boolean isScrollable) {
		this.isScrollable = isScrollable;
	}

}
