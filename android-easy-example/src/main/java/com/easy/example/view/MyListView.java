package com.easy.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easy.example.R;
import com.easy.view.EasyListView;

/**
 * Created by 11397 on 2016/5/31.
 */
public class MyListView extends EasyListView {

    private View header_icon;
    private TextView header_tv;
    private ProgressBar header_pb;
    private TextView footer_tv;
    private ProgressBar footer_pb;

    private RotateAnimation animUp;
    private RotateAnimation animDown;

    public MyListView(Context context) {
        super(context);
        init();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        animUp = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation
                .RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(300);
        animUp.setFillAfter(true);
        animDown = new RotateAnimation(180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation
                .RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(300);
        animDown.setFillAfter(true);
    }

    @Override
    protected View inflateRefreshHeader() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.header_list_view, null);
        header_icon = v.findViewById(R.id.icon);
        header_tv = (TextView) v.findViewById(R.id.tv);
        header_pb = (ProgressBar) v.findViewById(R.id.pb);
        return v;
    }

    @Override
    protected View inflateLoadFooter() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.footer_list_view, null);
        footer_tv = (TextView) v.findViewById(R.id.tv);
        footer_pb = (ProgressBar) v.findViewById(R.id.pb);
        return v;
    }

    @Override
    protected void showPulling(float newHeight) {

    }

    @Override
    protected void showPullingEnough() {
        header_pb.setVisibility(View.GONE);
        header_icon.setVisibility(View.VISIBLE);
        header_icon.startAnimation(animUp);
        header_tv.setText("松开刷新");
    }

    @Override
    protected void showPullingNotEnough(boolean changeStatusFormEnough) {
        header_pb.setVisibility(View.GONE);
        header_icon.setVisibility(View.VISIBLE);
        if (changeStatusFormEnough) {
            header_icon.startAnimation(animDown);
        } else {
            header_icon.clearAnimation();
        }
        header_tv.setText("下拉刷新");
    }

    @Override
    protected void showRefreshing() {
        header_pb.setVisibility(View.VISIBLE);
        header_icon.setVisibility(View.GONE);
        header_tv.setText("刷新中");
    }

    @Override
    protected void showRefreshFail() {
        header_pb.setVisibility(View.GONE);
        header_icon.setVisibility(View.GONE);
        header_tv.setText("刷新失败");
    }

    @Override
    protected void showLoadingPage() {
        footer_pb.setVisibility(View.VISIBLE);
        footer_tv.setText("加载中");
    }

    @Override
    protected void showLoadPageFail() {
        footer_pb.setVisibility(View.GONE);
        footer_tv.setText("加载失败");
    }

    @Override
    protected void showNoMore() {
        footer_pb.setVisibility(View.GONE);
        footer_tv.setText("没有更多");
    }
}
