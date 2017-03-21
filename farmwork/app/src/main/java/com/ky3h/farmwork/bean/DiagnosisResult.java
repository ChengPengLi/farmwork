package com.ky3h.farmwork.bean;

public class DiagnosisResult {
	
	private int id;
//	private int uid;
	private long createDate;
//	private long modifyDate;
	private String subject_sn;
	private String name;
	private String isRead;
//	private boolean isOnlyLocalTest;
//	private boolean isTestAlipay;
//	public boolean isTestAlipay() {
//		return isTestAlipay;
//	}
//	public void setTestAlipay(boolean isTestAlipay) {
//		this.isTestAlipay = isTestAlipay;
//	}
//	public boolean isOnlyLocalTest() {
//		return isOnlyLocalTest;
//	}
//	public void setOnlyLocalTest(boolean isOnlyLocalTest) {
//		this.isOnlyLocalTest = isOnlyLocalTest;
//	}
	public DiagnosisResult(int id, long createData, String subject_sn, String name, String isRead) {
		super();
		this.id = id;
		this.createDate = createData;
		this.subject_sn = subject_sn;
		this.name = name;
		this.isRead = isRead;
	}
	
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public int getUid() {
//		return uid;
//	}
//	public void setUid(int uid) {
//		this.uid = uid;
//	}
	public long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
//	public long getModifyDate() {
//		return modifyDate;
//	}
//	public void setModifyDate(long modifyDate) {
//		this.modifyDate = modifyDate;
//	}
	public String getSubject_sn() {
		return subject_sn;
	}
	public void setSubject_sn(String subject_sn) {
		this.subject_sn = subject_sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DiagnosisResult{" +
				"id=" + id +
				", createDate=" + createDate +
				", subject_sn='" + subject_sn + '\'' +
				", name='" + name + '\'' +
				", isRead='" + isRead + '\'' +
				'}';
	}
}
