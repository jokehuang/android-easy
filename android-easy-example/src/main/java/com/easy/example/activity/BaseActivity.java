package com.easy.example.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easy.activity.EasyActivity;
import com.easy.example.R;

import org.xutils.x;

/**
 * Created by Joke on 2016/3/23.
 */
public class BaseActivity extends EasyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setImmersion(0, 0);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(this.getClass().getSimpleName());
        RelativeLayout layout_content = (RelativeLayout) findViewById(R.id.layout_content);
        LayoutInflater.from(this).inflate(layoutResID, layout_content);
    }

    public void back(View v) {
        finish();
    }
}
