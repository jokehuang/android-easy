package com.easy.example.activity.view;

import android.view.View;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_view)
public class ViewActivity extends BaseActivity {

    @Event(value = R.id.btn_view_pager, type = View.OnClickListener.class)
    private void onClickViewPager(View view) {
        startActivity(ViewPagerActivity.class);
    }

    @Event(value = R.id.btn_fragment_pager, type = View.OnClickListener.class)
    private void onClickFragmentPager(View view) {
        startActivity(FragmentPagerActivity.class);
    }

    @Event(value = R.id.btn_edit_text, type = View.OnClickListener.class)
    private void onClickEditText(View view) {
        startActivity(EditTextActivity.class);
    }

    @Event(value = R.id.btn_list_view, type = View.OnClickListener.class)
    private void onClickListView(View view) {
        startActivity(ListViewActivity.class);
    }
}
