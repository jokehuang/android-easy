package com.easy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @Description 1、支持泛型item的存取操作 2、封装了view的重用 3、支持单选和多选模式
 * @author Joke Huang
 * @email 113979462@qq.com
 * @createDate 2015年1月19日
 * @version 1.2.0
 */

public abstract class EasyAdapter<T> extends BaseAdapter {
	/** 普通模式，用户不能选中item */
	public static final int MODE_NON = 0;
	/** 多选模式，用户可以选中多个item */
	@Deprecated
	public static final int MODE_CHECK_BOX = 1;
	public static final int MODE_MULTIPLE_SELECT = 1;
	/** 单选模式，用户只能选中其中一个item */
	@Deprecated
	public static final int MODE_RADIO_GROUP = 2;
	public static final int MODE_SINGLE_SELECT = 2;
	protected int mode = MODE_NON;

	protected Context context;
	protected List<T> items;
	// protected AdapterView<?> adapterView;
	// protected OnItemClickListener onItemClickListener;
	/** 已选中的item */
	protected List<T> selectedItems = new ArrayList<T>();

	public EasyAdapter(Context context) {
		this.context = context;
		this.items = new ArrayList<T>();
	}

	public EasyAdapter(Context context, List<T> items) {
		this.context = context;
		this.items = items;
	}

	/**
	 * 根据下标获取item，返回指定的泛型
	 * 
	 * @param position
	 * @return
	 */
	public T get(int position) {
		return items == null || position < 0 || position >= items.size() ? null
				: items.get(position);
	}

	/**
	 * 找到item对应的下标
	 * 
	 * @param item
	 * @return
	 */
	public int indexOf(T item) {
		return items == null ? -1 : items.indexOf(item);
	}

	/**
	 * 添加单个item
	 * 
	 * @param item
	 */
	public void add(T item) {
		if (items == null)
			return;
		items.add(item);
		notifyDataSetChanged();
	}

	/**
	 * 添加单个item到指定下标
	 * 
	 * @param position
	 * @param item
	 */
	public void add(int position, T item) {
		if (items == null)
			return;
		items.add(position, item);
		notifyDataSetChanged();
	}

	/**
	 * 添加多个item
	 * 
	 * @param items
	 */
	public void add(List<? extends T> items) {
		if (this.items == null)
			return;
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	/**
	 * 添加多个item，从指定下标开始插入
	 * 
	 * @param position
	 * @param items
	 */
	public void add(int position, List<? extends T> items) {
		if (this.items == null)
			return;
		this.items.addAll(position, items);
		notifyDataSetChanged();
	}

	/**
	 * 根据下标移除item
	 * 
	 * @param position
	 */
	public void remove(int position) {
		if (items == null)
			return;
		T item = items.remove(position);
		selectedItems.remove(item);
		notifyDataSetChanged();
	}

	/**
	 * 移除指定的item
	 * 
	 * @param item
	 */
	public void remove(T item) {
		if (items == null)
			return;
		items.remove(item);
		selectedItems.remove(item);
		notifyDataSetChanged();
	}

	/**
	 * 移除多个item
	 * 
	 * @param items
	 */
	public void remove(List<? extends T> items) {
		if (this.items == null)
			return;
		this.items.removeAll(items);
		selectedItems.removeAll(items);
		notifyDataSetChanged();
	}

	/**
	 * 移除全部item
	 */
	public void clear() {
		if (items == null)
			return;
		items.clear();
		selectedItems.clear();
		notifyDataSetChanged();
	}

	/**
	 * 获取item个数
	 */
	@Override
	public int getCount() {
		return items == null ? 0 : items.size();
	}

	/**
	 * 根据下标获取item，返回Object类型
	 */
	@Override
	public Object getItem(int position) {
		return items == null || position < 0 || position >= items.size() ? null
				: items.get(position);
	}

	/**
	 * 根据下标获取item的Id，默认等于下标，有别的要求可重写
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 根据下标初始化或重用item对应的view
	 */
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = newHolder();
			convertView = holder.init(LayoutInflater.from(context));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		holder.setSelected(isSelected(position));
		holder.update();

		return convertView;
	}

	/**
	 * 实例化一个holder，必须实现
	 */
	protected abstract ViewHolder newHolder();

	/**
	 * 此类用于保存并设置item对应的view的内容
	 * 
	 * @author Joke
	 *
	 */
	protected abstract class ViewHolder {
		/** 当前item的下标 */
		protected int position;
		/** 当前item是否被选中，mode为MODE_NON时一般为false */
		protected boolean isSelected;

		/**
		 * 
		 * 加载布局，查找出需要的子控件，保存到自己声明的成员变量，供set()方法使用
		 * 
		 * @param inflater
		 * @return 根布局
		 */
		protected abstract View init(LayoutInflater inflater);

		/**
		 * 设置当前item对应的ui或响应事件等
		 * 
		 */
		protected abstract void update();

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}

		// @Override
		// public void onClick(View v) {
		// onItemClickListener.onItemClick(adapterView, root, position,
		// getItemId(position));
		// }
	}

	// public void setOnItemClickListener(AdapterView<?> adapterView,
	// OnItemClickListener onItemClickListener) {
	// this.adapterView = adapterView;
	// this.onItemClickListener = onItemClickListener;
	// notifyDataSetChanged();
	// }

	/**
	 * 获取当前模式
	 * 
	 * @return
	 */
	public int getMode() {
		return this.mode;
	}

	/**
	 * 设置当前模式，具体值参考常量注释
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		if (this.mode != mode) {
			this.mode = mode;
			selectedItems.clear();
			notifyDataSetChanged();
		}
	}

	/**
	 * 判断对应下标的item是否被选中
	 * 
	 * @param position
	 * @return
	 */
	public boolean isSelected(int position) {
		return isSelected(get(position));
	}

	/**
	 * 判断指定的item是否被选中
	 * 
	 * @param item
	 * @return
	 */
	public boolean isSelected(T item) {
		return selectedItems.contains(item);
	}

	/**
	 * 反转选择对应下标的item
	 * 
	 * @param position
	 */
	public void reverseSelect(int position) {
		reverseSelect(get(position));
	}

	/**
	 * 反转选择指定的item
	 * 
	 * @param item
	 */
	public void reverseSelect(T item) {
		if (isSelected(item)) {
			unselect(item);
		} else {
			select(item);
		}
	}

	/**
	 * 选中对应下标的item
	 * 
	 * @param position
	 */
	public void select(int position) {
		select(get(position));
	}

	/**
	 * 选中指定的item
	 * 
	 * @param item
	 */
	public void select(T item) {
		if (items == null || !items.contains(item)) {
			return;
		}

		switch (mode) {
		case MODE_MULTIPLE_SELECT:
			selectedItems.remove(item);
			selectedItems.add(item);
			// Collections.sort(selections);
			notifyDataSetChanged();
			break;
		case MODE_SINGLE_SELECT:
			selectedItems.clear();
			selectedItems.add(item);
			// Collections.sort(selections);
			notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	/**
	 * 选中全部item
	 */
	public void selectAll() {
		selectedItems.clear();
		selectedItems.addAll(items);
		notifyDataSetChanged();
	}

	/**
	 * 取消选中全部item
	 */
	public void unselectAll() {
		selectedItems.clear();
		notifyDataSetChanged();
	}

	/**
	 * 取消选中对应下标的item
	 * 
	 * @param position
	 */
	public void unselect(int position) {
		unselect(get(position));
	}

	/**
	 * 取消选中指定的item
	 * 
	 * @param item
	 */
	public void unselect(T item) {
		selectedItems.remove(item);
		notifyDataSetChanged();
	}

	/**
	 * 返回选中的单个item的下标，仅供单选模式使用
	 * 
	 * @return
	 */
	public int getSelection() {
		return selectedItems.size() > 0 ? indexOf(selectedItems.get(0)) : -1;
	}

	/**
	 * 返回选中的全部item的下标
	 * 
	 * @return
	 */
	public List<Integer> getSelections() {
		List<Integer> result = new ArrayList<Integer>();
		for (T item : selectedItems) {
			int index = indexOf(item);
			if (index != -1)
				result.add(index);
		}
		return result;
	}

	/**
	 * 返回选中的单个item，仅供单选模式使用
	 * 
	 * @return
	 */
	public T getSelectedItem() {
		return selectedItems.size() > 0 ? selectedItems.get(0) : null;
	}

	/**
	 * 返回选中的全部item
	 * 
	 * @return
	 */
	public List<T> getSelectedItems() {
		return this.selectedItems;
	}
}
