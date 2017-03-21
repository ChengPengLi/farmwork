package com.ky3h.farmwork.bean;

public class Version {

    private boolean isUpdate;
    private String downurl;

    public Version() {
        isUpdate = false;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }
}
