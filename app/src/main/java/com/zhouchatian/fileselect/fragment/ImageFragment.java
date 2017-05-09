package com.zhouchatian.fileselect.fragment;


import android.content.Intent;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhouchatian.fileselect.bean.PictureBean;
import com.zhouchatian.fileselect.R;
import com.zhouchatian.fileselect.adapter.PictureAdapter;
import com.zhouchatian.fileselect.common.Actions;
import com.zhouchatian.fileselect.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 图片
 * Created by Mr.Robot on 2017/4/25.
 * https://github.com/TheSadFrog
 */

public class ImageFragment extends BaseFragment {


    private GridView gridView;
    private TextView tvSelectNum;
    private RelativeLayout contentView;
    private LinearLayout defaultView;

    private ArrayList listData;
    private int count =  0;//计算选中的个数

    @Override
    protected int getFragmentLayout() {
        return R.layout.fileselect_image_fragment;
    }

    @Override
    protected void intview(View view) {
        listData = new ArrayList<>();
        gridView = (GridView) view.findViewById(R.id.grid_view);
        tvSelectNum = (TextView) view.findViewById(R.id.tv_select_num);
        contentView = (RelativeLayout) view.findViewById(R.id.content_view);
        defaultView = (LinearLayout) view.findViewById(R.id.default_view);
    }

    @Override
    protected void initDate() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String pkName = context.getPackageName();
            File path = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/"+pkName);
            File[] files = path.listFiles();
            getFileName(files);
        }
        if (listData != null && listData.size() > 0) {
            contentView.setVisibility(View.VISIBLE);
            defaultView.setVisibility(View.GONE);
        } else {
            contentView.setVisibility(View.GONE);
            defaultView.setVisibility(View.VISIBLE);
        }
        PictureAdapter adapter = new PictureAdapter(listData,getContext());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PictureBean bean = (PictureBean) listData.get(position);
                CheckBox cb = (CheckBox) view.findViewById(R.id.check_box);
                if(cb.isChecked()){
                    cb.setChecked(false);
                    count -= 1;
                    bean.setCheck(false);
                } else {
                    cb.setChecked(true);
                    count += 1;
                    bean.setCheck(true);
                }
                tvSelectNum.setText("发送("+count+")");
            }
        });
        tvSelectNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> patchList =  new ArrayList<>();
                if (listData != null && listData.size() > 0) {
                    for (int i = 0; i < listData.size(); i++) {
                        PictureBean bean = (PictureBean) listData.get(i);
                        if(bean.isCheck()){
                            patchList.add(bean.getImagePatch());
                        }
                    }
                }
                //返回数据
                if (patchList.size() <= 0) {
                    ToastUtil.showShort(getActivity(), "请选择文件");
                    return;
                }
                Intent intent = new Intent();
                intent.setAction(Actions.CALLBACK_DATA);
                intent.putStringArrayListExtra("paths", patchList);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
    }

    /**
     * 循环遍历文件夹取出图片
     * 这个方法可以抽成一个工具类，包括文档，视频，图片，音乐和其他
     */
    private void getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileName(file.listFiles());
                } else {
                    String fileName = file.getName();
                    String path = file.getPath();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".bmp")) {
                        PictureBean bean = new PictureBean();
                        bean.setCheck(false);
                        bean.setName(fileName.substring(0, fileName.lastIndexOf(".")));
                        bean.setImagePatch(path);
                        listData.add(bean);
                    } else if (fileName.endsWith(".gif")) {
                        PictureBean bean = new PictureBean();
                        bean.setCheck(false);
                        bean.setName(fileName.substring(0, fileName.lastIndexOf(".")));
                        bean.setImagePatch(path);
                        listData.add(bean);
                    }
                }
            }
        }
    }
}
