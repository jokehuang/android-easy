package com.easy.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @Description 1、可设置单选按钮与页面绑定 2、自带简易的adapter 3、更多扩展功能
 * @author Joke Huang
 * @createDate 2015年1月7日
 * @version 1.1.0
 */

public class EasyPager extends ViewPager {

	private boolean isScrollable = true;// 是否允许滑动切换页面
	private RadioGroup rg;// 对应不同页面的单选按钮组
	private List<View> vs;// 不同页面，View的方式
	private List<Fragment> fs;// 不同页面，Fragment的方式
	private OnPageChangeListener opclCustom;// 自定义的页面切换监听器
	private boolean fragmentDistroyable = true;// 是否允许销毁远处的Fragment页面

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
	 * 默认的Fragment适配器，只在使用Fragment做页面的时候创建
	 */
	// private FragmentAdapter fragmentAdapter;

	class FragmentAdapter extends FragmentPagerAdapter {

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return fs.size();
		}

		@Override
		public Fragment getItem(int paramInt) {
			return fs.get(paramInt);
		}

		public void destroyItem(ViewGroup container, int position, Object object) {
			if (fragmentDistroyable) {
				// 如果允许销毁Fragment，调用父类方法执行销毁相关的生命周期
				super.destroyItem(container, position, object);
			}
		};
	};

	/**
	 * 默然的View适配器，只在使用View做页面的时候创建
	 */
	// private ViewAdapter viewAdapter;

	class ViewAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return vs.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(vs.get(position));
			return vs.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(vs.get(position));
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
	 * 添加页面
	 */
	public void addPages(View v) {
		if (vs == null) {
			vs = new ArrayList<View>();
			setAdapter(new ViewAdapter());
		}
		vs.add(v);
		getAdapter().notifyDataSetChanged();
	}

	/**
	 * 添加页面
	 */
	public void addPages(Fragment f, FragmentManager fm) {
		if (fs == null) {
			fs = new ArrayList<Fragment>();
			setAdapter(new FragmentAdapter(fm));
		}
		fs.add(f);
		getAdapter().notifyDataSetChanged();
	}

	/**
	 * 设置页面内容
	 */
	public void setPages(List<View> vs) {
		this.fs = null;
		this.vs = vs;
		setAdapter(new ViewAdapter());
	}

	/**
	 * 设置页面内容
	 */
	public void setPages(List<Fragment> fs, FragmentManager fm) {
		this.vs = null;
		this.fs = fs;
		setAdapter(new FragmentAdapter(fm));
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
	 * 获取当前的Fragment
	 */
	public Fragment getCurrentFragment() {
		return fs == null ? null : fs.get(getCurrentItem());
	}

	/**
	 * 获取当前的View
	 */
	public View getCurrentView() {
		return vs == null ? null : vs.get(getCurrentItem());
	}

	/**
	 * 获取对应下标的Fragment
	 */
	public Fragment getFragmentAt(int index) {
		try {
			return fs.get(index);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取对应下标的View
	 */
	public View getViewAt(int index) {
		try {
			return vs.get(index);
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

	public boolean isFragmentDistroyable() {
		return fragmentDistroyable;
	}

	public void setFragmentDistroyable(boolean fragmentDistroyable) {
		this.fragmentDistroyable = fragmentDistroyable;
	}

}
