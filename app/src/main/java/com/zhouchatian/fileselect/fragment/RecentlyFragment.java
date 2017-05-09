package com.zhouchatian.fileselect.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhouchatian.fileselect.R;
import com.zhouchatian.fileselect.adapter.TabLayoutAdapter;
import com.zhouchatian.fileselect.common.Actions;
import com.zhouchatian.fileselect.fragment.DocumentFragment;
import com.zhouchatian.fileselect.fragment.ImageFragment;
import com.zhouchatian.fileselect.fragment.MusicFragment;
import com.zhouchatian.fileselect.fragment.OtherFragment;
import com.zhouchatian.fileselect.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 最近
 * Created by Mr.Robot on 2017/4/26.
 * https://github.com/TheSadFrog
 */

public class RecentlyFragment extends BaseFragment {


    private TabLayout tabLayoutTitle;
    private ViewPager vPagerView;
    private FragmentPagerAdapter fAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private List<String> list_title = new ArrayList<>();;
    private callbackValue callback;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fileselect_recently_fragment;
    }

    public interface callbackValue {
        void sendData(ArrayList<String> data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (callbackValue)activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initFragments();

        //设置TabLayout的模式
        tabLayoutTitle.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tabLayoutTitle.addTab(tabLayoutTitle.newTab().setText(list_title.get(0)));
        tabLayoutTitle.addTab(tabLayoutTitle.newTab().setText(list_title.get(1)));
        tabLayoutTitle.addTab(tabLayoutTitle.newTab().setText(list_title.get(2)));
        tabLayoutTitle.addTab(tabLayoutTitle.newTab().setText(list_title.get(3)));
        tabLayoutTitle.addTab(tabLayoutTitle.newTab().setText(list_title.get(4)));

        fAdapter = new TabLayoutAdapter(getActivity().getSupportFragmentManager(), mFragments, list_title);
        vPagerView.setAdapter(fAdapter);
        tabLayoutTitle.setupWithViewPager(vPagerView);

    }

    private void initFragments() {
        DocumentFragment documentFragment = new DocumentFragment();
        VideoFragment videoFragment = new VideoFragment();
        ImageFragment imageFragment = new ImageFragment();
        MusicFragment musicFragment = new MusicFragment();
        OtherFragment otherFragment = new OtherFragment();
        mFragments.add(documentFragment);
        mFragments.add(videoFragment);
        mFragments.add(imageFragment);
        mFragments.add(musicFragment);
        mFragments.add(otherFragment);
        list_title.add("文档");
        list_title.add("视频");
        list_title.add("图片");
        list_title.add("音乐");
        list_title.add("其他");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null)
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        receiver = null;
    }

    private Receiver receiver;
    private class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Actions.CALLBACK_DATA.equals(intent.getAction())) {
                ArrayList<String> list = intent.getStringArrayListExtra("paths");
                if (callback != null) {
                    callback.sendData(list);
                }
            }
        }
    }

    @Override
    protected void intview(View view) {
        if (receiver != null) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
            receiver = null;
        }
        receiver = new Receiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,
                new IntentFilter(Actions.CALLBACK_DATA));
        tabLayoutTitle = (TabLayout) view.findViewById(R.id.tabLayout_title);
        vPagerView = (ViewPager) view.findViewById(R.id.vPager_view);
    }

    @Override
    protected void initDate() {

    }
}
