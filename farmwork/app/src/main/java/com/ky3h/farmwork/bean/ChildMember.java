package com.ky3h.farmwork.bean;

import java.io.Serializable;

/**
 * 子成员
 *
 * @author Administrator
 */
public class ChildMember implements Serializable {
    private long createDate;
    private int id;
    private String isMedicare;
    private String mobile;
    private long modifyDate;
    private String name;
    private String gender;
    private String birthday;
    private int weight;
    private String idcard;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsMedicare() {
        return isMedicare;
    }

    public void setIsMedicare(String isMedicare) {
        this.isMedicare = isMedicare;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
