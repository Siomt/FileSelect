package com.zhouchatian.fileselect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhouchatian.fileselect.R;
import com.zhouchatian.fileselect.adapter.DocumentAdapter;
import com.zhouchatian.fileselect.bean.DocumentBean;
import com.zhouchatian.fileselect.common.Actions;
import com.zhouchatian.fileselect.utils.FileSizeUtil;
import com.zhouchatian.fileselect.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;


/**
 * 视频
 * Created by Mr.Robot on 2017/4/25.
 * https://github.com/TheSadFrog
 */

public class VideoFragment extends BaseFragment {
    private ListView lv;
    private TextView tvSelectNum;
    private RelativeLayout contentView;
    private LinearLayout defaultView;

    private ArrayList listData;
    private int count = 0;//计算选中的个数

    @Override
    protected int getFragmentLayout() {
        return R.layout.fileselect_video_fragment;
    }

    @Override
    protected void intview(View view) {
        listData = new ArrayList<>();
        lv = (ListView) view.findViewById(R.id.lv);
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
        DocumentAdapter adapter = new DocumentAdapter(listData, getContext());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DocumentBean bean = (DocumentBean) listData.get(position);
                CheckBox cb = (CheckBox) view.findViewById(R.id.check_box_doc);
                if(cb.isChecked()){
                    cb.setChecked(false);
                    count -= 1;
                    bean.setCheck(false);
                }else {
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
                        DocumentBean bean = (DocumentBean) listData.get(i);
                        if(bean.isCheck()){
                            patchList.add(bean.getPatch());
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
     * 循环遍历文件夹取出视频文件
     * 这个方法可以抽成一个工具类，包括文档，视频，图片，音乐和其他
     */
    private void getFileName(File[] files) {
        if (files == null) {// 先判断目录是否为空，否则会报空指针
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                getFileName(file.listFiles());
            } else {
                String fileName = file.getName();
                String path = file.getPath();
                String size = FileSizeUtil.getAutoFileOrFilesSize(path);
                if (fileName.endsWith(".mp4")) {
                    DocumentBean bean = new DocumentBean();
                    String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                    bean.setName(name + ".mp4");
                    bean.setType(R.drawable.ic_video);
                    bean.setSize(size);
                    bean.setPatch(path);
                    bean.setCheck(false);
                    listData.add(bean);
                } else if (fileName.endsWith(".3gp")) {
                    DocumentBean bean = new DocumentBean();
                    String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                    bean.setName(name + ".3gp");
                    bean.setType(R.drawable.ic_video);
                    bean.setSize(size);
                    bean.setPatch(path);
                    bean.setCheck(false);
                    listData.add(bean);
                } else if (fileName.endsWith(".avi")) {
                    DocumentBean bean = new DocumentBean();
                    String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                    bean.setName(name + ".avi");
                    bean.setType(R.drawable.ic_video);
                    bean.setSize(size);
                    bean.setPatch(path);
                    bean.setCheck(false);
                    listData.add(bean);
                } else if (fileName.endsWith(".rmvb")) {
                    DocumentBean bean = new DocumentBean();
                    String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                    bean.setName(name + ".rmvb");
                    bean.setType(R.drawable.ic_video);
                    bean.setSize(size);
                    bean.setPatch(path);
                    bean.setCheck(false);
                    listData.add(bean);
                } else if (fileName.endsWith(".mov")) {
                    DocumentBean bean = new DocumentBean();
                    String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                    bean.setName(name + ".mov");
                    bean.setType(R.drawable.ic_video);
                    bean.setSize(size);
                    bean.setPatch(path);
                    bean.setCheck(false);
                    listData.add(bean);
                } else if (fileName.endsWith(".wmv")) {
                    DocumentBean bean = new DocumentBean();
                    String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                    bean.setName(name + ".wmv");
                    bean.setType(R.drawable.ic_video);
                    bean.setSize(size);
                    bean.setPatch(path);
                    bean.setCheck(false);
                    listData.add(bean);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

}
