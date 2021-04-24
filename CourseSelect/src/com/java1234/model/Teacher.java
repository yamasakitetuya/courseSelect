package com.java1234.model;

/**
 * 
 * @author Administrator
 *
 */
public class Teacher {

	private Integer id; // 
	private String userName; // 
	private String password; // 
	private String trueName; // 
	private String title; // ְ
	private int status;
	private String teaNo;
	
	
	
	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Teacher(String userName, String password, String trueName,
			String title, String teaNo, int status) {
		super();
		this.userName = userName;
		this.password = password;
		this.trueName = trueName;
		this.title = title;
		this.teaNo = teaNo;
		this.status = status;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getTeaNo() {
		return teaNo;
	}



	public void setTeaNo(String teaNo) {
		this.teaNo = teaNo;
	}
	
	
}
