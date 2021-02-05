package com.obadarawashdeh.retreivedata.Model;

public class InfoModel {
    private int img;
    private int desc;

    public InfoModel(int img, int desc) {
        this.img = img;
        this.desc = desc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }
}
