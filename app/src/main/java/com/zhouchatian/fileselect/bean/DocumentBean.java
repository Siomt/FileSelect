package com.zhouchatian.fileselect.bean;

/**
 * 选择文档的实体类
 * Created by Mr.Robot on 2017/4/26.
 * https://github.com/TheSadFrog
 */

public class DocumentBean {
    private String name;//文件名字
    private String patch;//路径
    private int type;//类型
    private String size;//大小
    private boolean check;//是否选中

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
