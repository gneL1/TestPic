package com.example.testapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.testapp.Fragment.BaseFragment;
import com.example.testapp.Fragment.FirstFragment;
import com.example.testapp.Fragment.FouthFragment;
import com.example.testapp.Fragment.SecondFragment;
import com.example.testapp.Fragment.ThirdFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.Toolbar)
    android.support.v7.widget.Toolbar Toolbar;
    @Bind(R.id.Fragment)
    FrameLayout Fragment;
    @Bind(R.id.Btn_Nv)
    BottomNavigationView BtnNv;


    //装fragment的实例集合
    private ArrayList<BaseFragment> fragments;

    private int position = 0;

    //缓存Fragment或上次显示的Fragment
    private Fragment tempFtagment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_background);
        }
        Toolbar.inflateMenu(R.menu.menu_toolbar);
        Toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this,"点击了更多按钮",Toast.LENGTH_SHORT).show();
                return true;
            }
        });



        //初始化Fragment
        initFragment();


        setListener();



        //把默认界面设置为HomePage
        Fragment baseFragment = fragments.get(0);

        //记得把tempFragment初始化，不然会重叠
        //参考switchFragment方法，不初始化，第一个参数为0而不是FirstFragment
        //跳转过去后FirstFragment未被隐藏
        tempFtagment = baseFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Fragment,baseFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    private void initFragment(){
        fragments = new ArrayList<>();//注意fragments是泛型BaseFragment,所以下面的增加的类要继承BaseFragment
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());
        fragments.add(new FouthFragment());
    }

    //根据位置得到的对应的Fragment
    private BaseFragment getFragment(int position){
        if (fragments != null && fragments.size() > 0){
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }



    private void setListener() {
        BtnNv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_app:
                        position = 0;
                        break;
                    case R.id.tab_build:
                        position = 1;
                        break;
                    case R.id.tab_folder:
                        position = 2;
                        break;
                    case R.id.tab_pen:
                        position = 3;
                        break;
                }
                //根据位置得到相应的Fragment
                BaseFragment baseFragment = getFragment(position);
                //第一个参数是上次显示的Fragment,第二个参数是当前正要显示的Fragment
                switchFragment(tempFtagment,baseFragment);
                return true;

            }
        });
    }




    //切换Fragment
    private void switchFragment(android.support.v4.app.Fragment fragment, BaseFragment nextFragment){
        if (tempFtagment != nextFragment){
            tempFtagment = nextFragment;
            if (nextFragment != null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加成功
                if (!nextFragment.isAdded()){
                    //隐藏当前的Fragment
                    if (fragment != null){
                        transaction.hide(fragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.Fragment,nextFragment).commit();
                }else {
                    //隐藏当前的Fragment
                    if (fragment != null){
                        transaction.hide(fragment);
                    }
                    //fragment是空的话就什么都不做
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }
}
