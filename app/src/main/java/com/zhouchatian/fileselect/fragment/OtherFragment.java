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
 * 其他
 * Created by Mr.Robot on 2017/4/25.
 * https://github.com/TheSadFrog
 */

public class OtherFragment extends BaseFragment {
    private ListView lv;
    private TextView tvSelectNum;
    private RelativeLayout contentView;
    private LinearLayout defaultView;

    private ArrayList listData;
    private int count =  0;//计算选中的个数
    @Override
    protected int getFragmentLayout() {
        return R.layout.fileselect_other_fragment;
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
     * 循环遍历文件夹取出所有文件，除了文档，视频，图片，音乐
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
                    if (check(fileName)) {
                        DocumentBean bean = new DocumentBean();
                        //String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                        String[] names = path.split("/");
                        bean.setName(names[names.length - 1]);
                        bean.setType(R.drawable.ic_other);
                        bean.setSize(size);
                        bean.setPatch(path);
                        bean.setCheck(false);
                        listData.add(bean);
                    }
                }
            }
        }
    }


    /**
     * 排除文档，视频，图片，音频
     *
     * @param fileName
     * @return
     */
    private boolean check(String fileName) {
        //排除文档
        if (fileName.endsWith(".txt")) {
            return false;
        }
        if (fileName.endsWith(".doc")) {
            return false;
        }
        if (fileName.endsWith(".docx")) {
            return false;
        }
        if (fileName.endsWith(".xls")) {
            return false;
        }
        if (fileName.endsWith(".xlsx")) {
            return false;
        }
        if (fileName.endsWith(".pdf")) {
            return false;
        }
        if (fileName.endsWith(".ppt")) {
            return false;
        }
        if (fileName.endsWith(".pptx")) {
            return false;
        }
        //排除视频
        if (fileName.endsWith(".mp4")) {
            return false;
        }
        if (fileName.endsWith(".3gp")) {
            return false;
        }
        if (fileName.endsWith(".avi")) {
            return false;
        }
        if (fileName.endsWith(".rmvb")) {
            return false;
        }
        if (fileName.endsWith(".mov")) {
            return false;
        }
        if (fileName.endsWith(".wmv")) {
            return false;
        }
        //排除图片
        if (fileName.endsWith(".jpg")) {
            return false;
        }
        if (fileName.endsWith(".png")) {
            return false;
        }
        if (fileName.endsWith(".bmp")) {
            return false;
        }
        if (fileName.endsWith(".gif")) {
            return false;
        }
        //排除音频
        if (fileName.endsWith(".mp3")) {
            return false;
        }
        if (fileName.endsWith(".wav")) {
            return false;
        }
        if (fileName.endsWith(".wmv")) {
            return false;
        }
        if (fileName.endsWith(".ogg")) {
            return false;
        }
        //排除其他
        if (fileName.endsWith(".0")) {
            return false;
        }
        if (!fileName.contains(".")) {
            return false;
        }
        return true;
    }
}
