package com.zhouchatian.fileselect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.zhouchatian.fileselect.adapter.TabViewPagerAdapter;
import com.zhouchatian.fileselect.fragment.LocalFragment;
import com.zhouchatian.fileselect.fragment.RecentlyFragment;
import com.zhouchatian.fileselect.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件选择首页
 * Created by Mr.Robot on 2017/4/26.
 * https://github.com/TheSadFrog
 */

public class FileSelectHomeActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener,
        LocalFragment.callbackValue, RecentlyFragment.callbackValue {
    private NoScrollViewPager myViewPage;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);
        initView();
        initViewPage();

    }

    public void initView() {
        myViewPage = (NoScrollViewPager) findViewById(R.id.id_viewpager);
        radioGroup = (RadioGroup) findViewById(R.id.top_click);
        radioGroup.setOnCheckedChangeListener(this);
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initViewPage() {

        myViewPage = (NoScrollViewPager) findViewById(R.id.id_viewpager);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new RecentlyFragment());//最近
        fragmentList.add(new LocalFragment());//本地
        TabViewPagerAdapter fragmentViewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        myViewPage.setAdapter(fragmentViewPagerAdapter);
        myViewPage.setOffscreenPageLimit(2);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.click_position) {
            myViewPage.setCurrentItem(0);
        } else {
            myViewPage.setCurrentItem(1);
        }

    }

    @Override
    public void sendData(ArrayList<String> data) {
        if (data != null && data.size() > 0) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("paths", data);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
