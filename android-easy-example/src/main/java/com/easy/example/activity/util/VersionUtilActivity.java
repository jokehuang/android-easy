package com.easy.example.activity.util;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.EmptyUtil;
import com.easy.util.VersionUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_version_util)
public class VersionUtilActivity extends BaseActivity implements TextWatcher {
    @ViewInject(R.id.et_version1)
    private EditText et_version1;
    @ViewInject(R.id.et_version2)
    private EditText et_version2;
    @ViewInject(R.id.tv_result)
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateResult();
        et_version1.addTextChangedListener(this);
        et_version2.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (EmptyUtil.isEmpty(s.toString())) {
            tv_result.setText("Please input version");
        } else {
            updateResult();
        }
    }

    private void updateResult() {
        int result = VersionUtil.compare(et_version1.getText().toString(), et_version2.getText().toString());
        if (result > 0) {
            tv_result.setText("Version1 newer than Version2");
        } else if (result < 0) {
            tv_result.setText("Version1 older than Version2");
        } else {
            tv_result.setText("Version1 equals Version2");
        }
    }
}
