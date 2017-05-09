package com.zhouchatian.fileselect.fragment;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
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
 * 音乐
 * Created by Mr.Robot on 2017/4/25.
 * https://github.com/TheSadFrog
 */

public class MusicFragment extends BaseFragment {
    private ListView lv;
    private TextView tvSelectNum;
    private RelativeLayout contentView;
    private LinearLayout defaultView;

    private ArrayList listData;
    private int count = 0;//计算选中的个数

    @Override
    protected int getFragmentLayout() {
        return R.layout.fileselect_music_fragment;
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
                if (cb.isChecked()) {
                    cb.setChecked(false);
                    count -= 1;
                    bean.setCheck(false);
                } else {
                    cb.setChecked(true);
                    count += 1;
                    bean.setCheck(true);
                }
                tvSelectNum.setText("发送(" + count + ")");
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
     * 循环遍历文件夹取出音频文件
     */
    private void getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileName(file.listFiles());
                } else {
                    String fileName = file.getName();
                    String path = file.getPath();
                    String size = FileSizeUtil.getAutoFileOrFilesSize(path);
                    if (fileName.endsWith(".mp3")) {
                        DocumentBean bean = new DocumentBean();
                        String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                        bean.setName(name + ".mp3");
                        bean.setType(R.drawable.ic_music);
                        bean.setSize(size);
                        bean.setPatch(path);
                        bean.setCheck(false);
                        listData.add(bean);
                    } else if (fileName.endsWith(".ogg")) {
                        DocumentBean bean = new DocumentBean();
                        String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                        bean.setName(name + ".ogg");
                        bean.setType(R.drawable.ic_music);
                        bean.setSize(size);
                        bean.setPatch(path);
                        bean.setCheck(false);
                        listData.add(bean);
                    } else if (fileName.endsWith(".wav")) {
                        DocumentBean bean = new DocumentBean();
                        String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                        bean.setName(name + ".wav");
                        bean.setType(R.drawable.ic_music);
                        bean.setSize(size);
                        bean.setPatch(path);
                        bean.setCheck(false);
                        listData.add(bean);
                    }
                }
            }
        }
    }

}
