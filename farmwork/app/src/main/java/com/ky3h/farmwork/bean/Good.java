package com.ky3h.farmwork.bean;

import java.io.Serializable;

/**
 * Created by lipengcheng on 2016/6/29.
 * you are sb
 */
public class Good implements Serializable {
    private int goodResId;
    private String goodName;

    public Good(int goodResId, String goodName) {
        this.goodResId = goodResId;
        this.goodName = goodName;
    }

    public int getGoodResId() {
        return goodResId;
    }

    public void setGoodResId(int goodResId) {
        this.goodResId = goodResId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
}
