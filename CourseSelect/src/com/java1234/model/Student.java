package com.java1234.model;

/**
 * 
 * @author Administrator
 *
 */
public class Student {

	private Integer id;  // 
	private String userName; // 
	private String password; // 
	private String trueName; // 
	private String stuNo; // 
	private String professional; //
	private int status;
	
	
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Student(String userName, String password,
			String trueName, String stuNo, String professional,int status) {
		super();
		this.userName = userName;
		this.password = password;
		this.trueName = trueName;
		this.stuNo = stuNo;
		this.professional = professional;
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
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
