package com.ky3h.farmwork.bean;

import android.os.Build;

/**
 * 设备信息实体类
 * Created by Zhenyu on 2015/8/21.
 */
public class DeviceInfo {

    private String model;
    private String osver;
    private String userAgentName;
    private String userAgent;
    private int widthPixels;
    private int heightPixels;
    private String softver;
    private String language;
    private String country;

    public DeviceInfo() {
        model = Build.MODEL;
        osver = Build.VERSION.RELEASE;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOsver() {
        return osver;
    }

    public int getWidthPixels() {
        return widthPixels;
    }

    public void setWidthPixels(int widthPixels) {
        this.widthPixels = widthPixels;
    }

    public int getHeightPixels() {
        return heightPixels;
    }

    public void setHeightPixels(int heightPixels) {
        this.heightPixels = heightPixels;
    }

    public String getSoftver() {
        return softver;
    }

    public void setSoftver(String softver) {
        this.softver = softver;
    }

    public String getUserAgent() {
        if (userAgent == null) {
            userAgent = userAgentName + "/" + softver + " ( Android " + osver + "; " + language + "-"
                    + country + "; " + Build.MANUFACTURER + "; " + Build.BRAND + "; " + model + "; "
                    + Build.HARDWARE + "; " + widthPixels + "*" + heightPixels + " ) " + Build.CPU_ABI;
        }
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgentName() {
        return userAgentName;
    }

    public void setUserAgentName(String userAgentName) {
        this.userAgentName = userAgentName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
