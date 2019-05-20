package com.example.testapp.Fragment;

import android.view.View;

import com.example.testapp.R;

public class FirstFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.first_page,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
