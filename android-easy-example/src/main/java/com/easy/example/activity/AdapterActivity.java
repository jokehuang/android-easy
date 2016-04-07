package com.easy.example.activity;

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
import com.easy.example.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年6月8日
 */

@ContentView(R.layout.activity_adapter)
public class AdapterActivity extends BaseActivity {
	@ViewInject(R.id.lv)
	private ListView lv;
	@ViewInject(R.id.tv_selection)
	private TextView tv_selection;
	private EasyAdapter<String> eAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	@Event(value = R.id.lv, type = AdapterView.OnItemClickListener.class)
	private void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
		eAdapter.reverseSelect(position);
		showSelection();
	}

	/**
	 * 添加一个item
	 *
	 * @param v
	 */
	@Event(value = R.id.btn_add, type = View.OnClickListener.class)
	private void onClickAdd(View v) {
		eAdapter.add("item" + eAdapter.getCount());
		showSelection();
	}

	/**
	 * 移除一个item
	 *
	 * @param v
	 */
	@Event(value = R.id.btn_remove, type = View.OnClickListener.class)
	private void onClickRemove(View v) {
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
	@Event(value = R.id.btn_clear, type = View.OnClickListener.class)
	private void onClickClear(View v) {
		eAdapter.clear();
		showSelection();
	}

	/**
	 * 切换多选或单选模式
	 *
	 * @param radiogroup
	 * @param id
	 */
	@Event(value = R.id.rg_mode, type = RadioGroup.OnCheckedChangeListener.class)
	private void onCheckedChanged(RadioGroup radiogroup, int id) {
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
