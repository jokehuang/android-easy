package com.easy.example.activity.view;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.NetworkUtil;
import com.easy.view.EasyListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_list_view)
public class ListViewActivity extends BaseActivity implements EasyListView.OnRefreshListener, EasyListView
        .OnLoadPageListener {

    @ViewInject(R.id.lv)
    private EasyListView lv;
    private ArrayAdapter<String> adapter;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<>(this, R.layout.item_list_view);
        lv.setAdapter(adapter);
        lv.setOnRefreshListener(this);
        lv.setOnLoadPageListener(this);
        lv.refreshing();
    }

    @Override
    public void onLoadingPage(final int page) {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
            runnable = null;
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                if (page == 0) {
                    if (NetworkUtil.isNetworkConnected(self)) {
                        //刷新成功
                        lv.refreshSuccess();
                        adapter.clear();
                        for (int i = 0; i < 10; i++) {
                            adapter.add("page" + page + ": item" + i);
                        }
                    } else {
                        //刷新失败
                        lv.refreshFail();
                    }
                } else {
                    if (NetworkUtil.isNetworkConnected(self)) {
                        //加载成功
                        lv.loadPageSuccess();
                        for (int i = 0; i < 10; i++) {
                            adapter.add("page" + page + ": item" + i);
                        }
                        if (page >= 3) {
                            //没有更多
                            lv.noMore();
                        }
                    } else {
                        //加载失败
                        lv.loadPageFail();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        };

        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onRefreshing() {
        onLoadingPage(0);
    }
}
