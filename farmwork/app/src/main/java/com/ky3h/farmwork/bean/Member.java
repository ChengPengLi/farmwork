package com.ky3h.farmwork.bean;

import java.util.List;

public class Member {
	private int id;
	private String memberImage;
	private String gender;
	private String username;// 手机号
	private String name;// 姓名
	private String isMarried;// 婚否
	private String nation;// 民族
	private String identityType;// 证件类型
	private String idNumber;// 证件号码
	private String address;// 居住地
	private String phone;// 固话
	private String birthday;//出生日期
	private List<ChildMember> mengberchild;

	private transient String password;

	public Member() {
	}

	public Member(int id,String memberImage, String gender, String username,
				  String name, String isMarried, String nation, String identityType,
				  String idNumber, String address, String phone) {
		this.id = id;
		this.memberImage = memberImage;
		this.gender = gender;
		this.username = username;
		this.name = name;
		this.isMarried = isMarried;
		this.nation = nation;
		this.identityType = identityType;
		this.idNumber = idNumber;
		this.address = address;
		this.phone = phone;
		//this.birthday=birthday;
	}

	@Override
	public String toString() {
		return "Member [memberImage=" + memberImage + ", gender=" + gender
				+ ", username=" + username + ", name=" + name + ", isMarried="
				+ isMarried + ", nation=" + nation + ", identityType="
				+ identityType + ", idNumber=" + idNumber + ", address="
				+ address + ", phone=" + phone + ", birthday=" + birthday + ", user_id=" + id + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(String memberImage) {
		this.memberImage = memberImage;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserName() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(String isMarried) {
		this.isMarried = isMarried;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
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

	public List<ChildMember> getMengberchild() {
		return mengberchild;
	}

	public void setMengberchild(List<ChildMember> mengberchild) {
		this.mengberchild = mengberchild;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
