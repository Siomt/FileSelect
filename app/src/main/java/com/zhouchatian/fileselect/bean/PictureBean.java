package com.zhouchatian.fileselect.bean;

/**
 * 选择图片的实体类
 * Created by Mr.Robot on 2017/4/26.
 * https://github.com/TheSadFrog
 */

public class PictureBean {
    private String name;//图片名字
    private Boolean isCheck = false;//是否选中
    private String imagePatch;//图片路径

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public String getImagePatch() {
        return imagePatch;
    }

    public void setImagePatch(String imagePatch) {
        this.imagePatch = imagePatch;
    }
}
