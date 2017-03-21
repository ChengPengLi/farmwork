package com.ky3h.farmwork.bean;


/*
 * 个人信息User模型
 */
public class User {

    private int id;//memberId
    private String userName;//手机号码
    private String name;//用户昵称
    private String gender;//个人资料性别
    private String birthDate;//个人资料生日
    private int status;
    private String identityType;//证件类型
    private String idNumber;//身份证
    private String address;//地址
    private String phone;//固定电话
    private int memberChildId;
    private String memberImage;//个人头像
    private String isMedicare;//是否有医保
    private String JSESSIONID;
    private String token;

    public User(int id, String userName, String name, String gender, String birthDate, int status, String identityType, String idNumber, String address, String phone, int memberChildId, String memberImage, String isMedicare, String JSESSIONID, String token) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.status = status;
        this.identityType = identityType;
        this.idNumber = idNumber;
        this.address = address;
        this.phone = phone;
        this.memberChildId = memberChildId;
        this.memberImage = memberImage;
        this.isMedicare = isMedicare;
        this.JSESSIONID = JSESSIONID;
        this.token = token;
    }

    public User() {
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJSESSIONID(String jSESSIONID) {
        JSESSIONID = jSESSIONID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMemberChildId() {
        return memberChildId;
    }

    public void setMemberChildId(int mengberchild) {
        this.memberChildId = mengberchild;
    }

    public String getMemberImage() {
        return memberImage;
    }

    public void setMemberImage(String memberImage) {
        this.memberImage = memberImage;
    }

    public String getIsMedicare() {
        return isMedicare;
    }

    public void setIsMedicare(String isMedicare) {
        this.isMedicare = isMedicare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
