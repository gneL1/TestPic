package com.example.testapp.Fragment;

import android.view.View;

import com.example.testapp.R;

public class ThirdFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.third_page,null);
        return view;
    }
}
