package com.java1234.model;

/**
 * 
 * @author Administrator
 *
 */
public class Course {

	private Integer id; // 
	private String courseName; // 
	private String courseNo;
	private Integer credit; // 
	private Integer teacherId; // 
	private String teacherName;  // 
	private String courseTime;
	private String classroom;
	
	
	
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Course(String courseName, Integer credit, Integer teacherId) {
		super();
		this.courseName = courseName;
		this.credit = credit;
		this.teacherId = teacherId;
	}
	
	public String getCourseTime() {
		return  courseTime;
	}
	
	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


	public String getCourseNo() {
		return courseNo;
	}


	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}


	public String getClassroom() {
		return classroom;
	}


	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	
	
}
