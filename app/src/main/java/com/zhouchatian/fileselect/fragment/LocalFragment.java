package com.zhouchatian.fileselect.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

//import com.leon.lfilepickerlibrary.LFilePicker;
//import com.leon.lfilepickerlibrary.utils.Constant;
import com.zhouchatian.fileselect.R;

import java.util.ArrayList;


/**
 * 本地
 * Created by Mr.Robot on 2017/4/26.
 * https://github.com/TheSadFrog
 */

public class LocalFragment extends BaseFragment {
    private LinearLayout mobileMemory;
    private LinearLayout mobileSdcard;

    private LocalFragment mFragment;
    private callbackValue callback;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fileselect_local_fragment;
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
    protected void intview(View view) {
        this.mFragment = this;
        mobileMemory = (LinearLayout) view.findViewById(R.id.ll_mobile_memory);
        mobileSdcard = (LinearLayout) view.findViewById(R.id.ll_mobile_sdcard);
    }

    @Override
    protected void initDate() {
//        mobileMemory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new LFilePicker().withSupportFragment(mFragment)
//                        .withRequestCode(1001)
//                        .withTitle("文件选择")
//                        .withType("1")
//                        .withBackIcon(Constant.BACKICON_STYLEONE)
//                        .withIconStyle(Constant.ICON_STYLE_YELLOW)
//                        .start();
//            }
//        });
//
//        mobileSdcard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new LFilePicker().withSupportFragment(mFragment)
//                        .withRequestCode(1001)
//                        .withTitle("文件选择")
//                        .withType("2")
//                        .withBackIcon(Constant.BACKICON_STYLEONE)
//                        .withIconStyle(Constant.ICON_STYLE_YELLOW)
//                        .start();
//            }
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) {
                ArrayList<String> list = data.getStringArrayListExtra("paths");
                if (callback != null) {
                    callback.sendData(list);
                }
            }
        }
    }
}
