package com.java1234.model;

/**
 * student-course
 * @author Administrator
 *
 */
public class StudentCourse {

	private Integer id; // 
	private Integer studentId; // 
	private String studentName; // 
	private Integer courseId; // 
	private String courseName; // 
	private Integer credit; // 
	private Integer score; // 
	private Integer finish;//
	private String teacherName; // 
	private String courseTime;
	private String courseNo;//
	private String classroom;//
	
	public String getCourseTime() {
		return courseTime;
	}
	
	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}
	
	public Integer getFinish() {
		return finish;
	}
	
	public void setFinish(Integer fin) {
		this.finish = fin;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
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
