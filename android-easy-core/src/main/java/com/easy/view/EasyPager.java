package com.easy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.easy.util.EmptyUtil;
import com.easy.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joke Huang
 * @version 1.1.0
 * @Description 1、可设置单选按钮与页面绑定 2、自带简易的adapter 3、更多扩展功能
 * @createDate 2015年1月7日
 */

abstract class EasyPager<T> extends ViewPager {
	protected static final int LOOP_ORIGIN = Integer.MAX_VALUE / 2;

	protected boolean isScrollable = true;// 是否允许滑动
	protected boolean isLoop = false;//是否循环滑动
	protected boolean isAutoScroll = false;//是否自动滚动
	protected RadioGroup rg;// 对应页面的单选按钮组
	protected List<T> ls;// 页面
	protected OnPageChangeListener opclCustom;// 自定义的页面切换监听器
	protected Handler autoScrollHandler = new Handler();//自动滚动定时处理器
	protected Runnable autoScrollRunnable;
	protected long autoScrollInterval = 3000;//自动滚动间隔时间

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
			if (isLoop) {
				arg0 = toUnLoopPosition(arg0);
			}
			// 回调自定义的监听器
			if (opclCustom != null) {
				opclCustom.onPageScrolled(arg0, arg1, arg2);
			}
		}

		@Override
		public void onPageSelected(int arg0) {
			if (isLoop) {
				arg0 = toUnLoopPosition(arg0);
			}
			// 切换页面后选中对应的按钮
			if (rg != null && arg0 < rg.getChildCount()) {
				int id = rg.getChildAt(arg0).getId();
				if (id != rg.getCheckedRadioButtonId()) {
					rg.setOnCheckedChangeListener(null);
					rg.check(id);
					rg.setOnCheckedChangeListener(occl);
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
				if (rg.getChildAt(i).getId() == checkedId && i < getAdapter().getCount() && i !=
						getCurrentItem()) {
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
		int id = rg.getChildAt(getCurrentItem()).getId();
		rg.check(id);
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
	 * 开始自动滚动
	 */
	public void startAutoScroll() {
		isAutoScroll = true;
		if (autoScrollRunnable == null) {
			autoScrollRunnable = new Runnable() {
				@Override
				public void run() {
					EasyPager.super.setCurrentItem(EasyPager.super.getCurrentItem() + 1, true);
					autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollInterval);
				}
			};
		} else {
			autoScrollHandler.removeCallbacks(autoScrollRunnable);
		}
		autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollInterval);
	}

	/**
	 * 停止自动滚动
	 */
	public void stopAutoScroll() {
		isAutoScroll = false;
		if (autoScrollRunnable != null) {
			autoScrollHandler.removeCallbacks(autoScrollRunnable);
		}
	}

	/**
	 * 暂停自动滚动
	 */
	public void pauseAutoScroll() {
		if (autoScrollRunnable != null) {
			autoScrollHandler.removeCallbacks(autoScrollRunnable);
		}
	}

	/**
	 * 设置当前为哪一页，循环模式下跨页跳转会卡住，要逐页设置
	 */
	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		if(isAutoScroll){
			startAutoScroll();
		}
		if (isLoop) {
			item = toLoopPosition(item);
			int curLoopItem = super.getCurrentItem();
			if (item == curLoopItem) {
				return;
			}
			int increase = item > curLoopItem ? 1 : -1;
			int i = super.getCurrentItem();
			do {
				i += increase;
				super.setCurrentItem(i, smoothScroll);
			} while (i != item);
		} else {
			super.setCurrentItem(item, smoothScroll);
		}
	}

	@Override
	public void setCurrentItem(int item) {
		if(isAutoScroll){
			startAutoScroll();
		}
		if (isLoop) {
			item = toLoopPosition(item);
			int curLoopItem = super.getCurrentItem();
			if (item == curLoopItem) {
				return;
			}
			int increase = item > curLoopItem ? 1 : -1;
			int i = super.getCurrentItem();
			do {
				i += increase;
				super.setCurrentItem(i);
			} while (i != item);
		} else {
			super.setCurrentItem(item);
		}
	}

	@Override
	public int getCurrentItem() {
		if (isLoop) {
			return toUnLoopPosition(super.getCurrentItem());
		} else {
			return super.getCurrentItem();
		}
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
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() != 2) {
			LogUtil.e0("onTouchEvent: " + ev.getAction());
		}
		if (isAutoScroll) {
			if (ev.getAction() == MotionEvent.ACTION_DOWN) {
				pauseAutoScroll();
			}
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				startAutoScroll();
			}
		}
		if (isScrollable == false) {
			return false;
		} else {
			return super.onTouchEvent(ev);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() != 2) {
			LogUtil.e0("onInterceptTouchEvent: " + ev.getAction());
		}
		if (isAutoScroll) {
			if (ev.getAction() == MotionEvent.ACTION_DOWN) {
				pauseAutoScroll();
			}
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				startAutoScroll();
			}
		}
		if (isScrollable == false) {
			return false;
		} else {
			return super.onInterceptTouchEvent(ev);
		}
	}

	protected int toLoopPosition(int position) {
		if (EmptyUtil.isEmpty(ls)) {
			return LOOP_ORIGIN;
		}
		int curLoopPosition = super.getCurrentItem();
		int curUnLoopPosition = toUnLoopPosition(curLoopPosition);
		return curLoopPosition + (position - curUnLoopPosition);
	}

	protected int toUnLoopPosition(int position) {
		if (EmptyUtil.isEmpty(ls)) {
			return 0;
		}
		position = (position - LOOP_ORIGIN) % ls.size();
		if (position < 0) {
			position += ls.size();
		}
		return position;
	}

	public long getAutoScrollInterval() {
		return autoScrollInterval;
	}

	public void setAutoScrollInterval(long autoScrollInterval) {
		this.autoScrollInterval = autoScrollInterval;
	}

	public boolean isAutoScroll() {
		return isAutoScroll;
	}

	public void setAutoScroll(boolean isAutoScroll) {
		if (isAutoScroll) {
			startAutoScroll();
		} else {
			stopAutoScroll();
		}
	}

	public boolean isScrollable() {
		return isScrollable;
	}

	public void setScrollable(boolean isScrollable) {
		this.isScrollable = isScrollable;
	}

	public boolean isLoop() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		if (this.isLoop == isLoop) {
			return;
		}
		this.isLoop = isLoop;
		notifyDataSetChanged();
		if (isLoop) {
			super.setCurrentItem(LOOP_ORIGIN);
		}
	}
}
