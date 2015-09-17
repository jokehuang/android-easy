package com.easy.example;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.easy.activity.EasyActivity;
import com.easy.adapter.EasyAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年6月8日
 * @version 1.0.0
 */

@ContentView(R.layout.activity_adapter)
public class AdapterActivity extends EasyActivity {
	@ViewInject(R.id.lv)
	private ListView lv;
	@ViewInject(R.id.tv_selection)
	private TextView tv_selection;
	private EasyAdapter<String> eAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		eAdapter = new MyAdapter(self);
		lv.setAdapter(eAdapter);
		showSelection();
	}

	/**
	 * 多选或单选时，对item进行反选
	 * 
	 * @param adapterview
	 * @param view
	 * @param position
	 * @param id
	 */
	@OnItemClick(R.id.lv)
	public void onItemClick(AdapterView<?> adapterview, View view,
			int position, long id) {
		eAdapter.reverseSelect(position);
		showSelection();
	}

	/**
	 * 添加一个item
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_add)
	public void onClickAdd(View v) {
		eAdapter.add("item" + eAdapter.getCount());
		showSelection();
	}

	/**
	 * 移除一个item
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_remove)
	public void onClickRemove(View v) {
		if (eAdapter.getCount() > 0) {
			eAdapter.remove(eAdapter.getCount() - 1);
			showSelection();
		}
	}

	/**
	 * 清空所有item
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_clear)
	public void onClickClear(View v) {
		eAdapter.clear();
		showSelection();
	}

	/**
	 * 切换多选或单选模式
	 * 
	 * @param radiogroup
	 * @param id
	 */
	@OnRadioGroupCheckedChange(R.id.rg_mode)
	public void onCheckedChanged(RadioGroup radiogroup, int id) {
		int mode = eAdapter.getMode();
		switch (id) {
		case R.id.rb_non:
			mode = EasyAdapter.MODE_NON;
			break;
		case R.id.rb_single_select:
			mode = EasyAdapter.MODE_SINGLE_SELECT;
			break;
		case R.id.rb_multiple_select:
			mode = EasyAdapter.MODE_MULTIPLE_SELECT;
			break;
		default:
			break;
		}
		eAdapter.setMode(mode);
		showSelection();
	}

	/**
	 * 显示已选中的item
	 */
	private void showSelection() {
		StringBuilder sb = new StringBuilder();
		List<Integer> selections = eAdapter.getSelections();
		List<String> selectedItems = eAdapter.getSelectedItems();
		for (int i = 0; i < selections.size(); i++) {
			sb.append(selections.get(i) + ": " + selectedItems.get(i) + "\n");
		}
		tv_selection.setText(sb.toString());
	}

	class MyAdapter extends EasyAdapter<String> {
		public MyAdapter(Context context) {
			super(context);
		}

		@Override
		protected EasyAdapter<String>.ViewHolder newHolder() {
			return new ViewHolder() {
				private TextView tv;

				@Override
				protected View init(LayoutInflater inflater) {
					tv = new TextView(context);
					return tv;
				}

				@Override
				protected void update() {
					tv.setText(get(position));
					tv.setBackgroundColor(isSelected ? 0x66000066 : 0x00000000);
				}
			};
		}
	}
}
