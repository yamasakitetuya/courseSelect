package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.Course;
import com.java1234.model.StudentCourse;

/**
 * studentCourse Dao class
 * @author Administrator
 *
 */
public class StudentCourseDao {

	/**
	 * search grades and students from certain teacher
	 * @param con
	 * @param teacherId
	 * @return
	 * @throws Exception
	 */
	public List<StudentCourse> findStudentsByTeacherId(Connection con,Integer teacherId)throws Exception{
		List<StudentCourse> studentList=new ArrayList<StudentCourse>();
		String sql="SELECT t4.id AS id,t2.trueName AS studentName,t3.courseName AS courseName,t4.score AS score FROM t_teacher t1,t_student t2,t_course t3,t_student_course t4 WHERE t2.id=t4.studentId AND t3.id=t4.courseId AND t3.finish=1 AND t3.teacherId=t1.id AND t1.id="+teacherId;
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			StudentCourse student=new StudentCourse();
			student.setId(rs.getInt("id"));
			student.setStudentName(rs.getString("studentName"));
			student.setCourseName(rs.getString("courseName"));
			student.setScore(rs.getInt("score"));
			studentList.add(student);
		}
		return studentList;
	}
	
	/**
	 * add/alter grade by id 
	 * @param id
	 */
	public int updateScore(Connection con,int score,int id)throws Exception{
		String sql="update t_student_course set score=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, score);
		pstmt.setInt(2, id);
		return pstmt.executeUpdate();
	}
	
	/**
	 * search current course by stuid 
	 * @param con
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<StudentCourse> findCourseByStudentId(Connection con,int studentId)throws Exception{
		List<StudentCourse> courseList=new ArrayList<StudentCourse>();
		String sql="SELECT t4.id as id,t3.id as courseId,t3.courseName AS courseName,t3.credit AS credit,t1.trueName AS teacherName,t4.score AS score, t3.courseTime AS time, t3.classroom AS classroom FROM t_teacher t1,t_student t2,t_course t3,t_student_course t4 WHERE t2.id=t4.studentId AND t3.id=t4.courseId AND t3.teacherId=t1.id AND t4.finish = 0 AND t2.id="+studentId;
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			StudentCourse studentCourse=new StudentCourse();
			studentCourse.setId(rs.getInt("id"));
			studentCourse.setCourseId(rs.getInt("courseId"));
			studentCourse.setCourseName(rs.getString("courseName"));
			studentCourse.setCredit(rs.getInt("credit"));
			studentCourse.setTeacherName(rs.getString("teacherName"));
			studentCourse.setScore(rs.getInt("score"));
			studentCourse.setCourseTime(rs.getString("time"));
			studentCourse.setClassroom(rs.getString("classroom"));
			courseList.add(studentCourse);
		}
		return courseList;
	}
	/**
	 * 
	 * @param con
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<Course> findOpenedCourseByStudentProfession(Connection con,int studentId) throws Exception{
		List<Course> courseList = new ArrayList<Course>();
		String sql = "SELECT t5.id as id ,t3.courseNo as courseNo, t4.courseName as courseName,t5.courseTime as courseTime, t5.credit as credit, t5.classroom as classroom ,t6.trueName as teacherName FROM t_student t1, t_profession t2, t_profession_course t3 ,t_courselist t4, t_course t5, t_teacher t6 WHERE t1.professional = t2.professionName AND t2.professionNo = t3.professionNo AND t3.courseNo = t4.courseNo AND t4.courseName = t5.courseName AND t5.finish=1 AND t5.teacherId=t6.id And t1.id=" +studentId;
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Course professionCourse = new Course();
			professionCourse.setId(rs.getInt("id"));
			professionCourse.setCourseNo(rs.getString("courseNo"));
			professionCourse.setCourseName(rs.getString("courseName"));
			professionCourse.setCourseTime(rs.getString("courseTime"));
			professionCourse.setCredit(rs.getInt("credit"));
			professionCourse.setClassroom(rs.getString("classroom"));
			professionCourse.setTeacherName(rs.getString("teacherName"));
			courseList.add(professionCourse);
		}
		return courseList;
	}
	
	
	public List<Course> findAllProfessionalCourseList(Connection con,int studentId) throws Exception{
		List<Course> courseList = new ArrayList<Course>();
		String sql="SELECT t4.courseNo as courseNo, t4.courseName as courseName FROM t_student t1, t_profession t2, t_profession_course t3, t_courselist t4 WHERE t1.professional = t2.professionName AND t2.professionNo = t3.professionNo AND t3.courseNo=t4.courseNo AND t1.id ="+studentId;
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Course professionalCourse = new Course();
			professionalCourse.setCourseName(rs.getString("courseName"));
			professionalCourse.setCourseNo(rs.getString("courseNo"));
			courseList.add(professionalCourse);
		}
		return courseList;
	}
	
	
	
	/**
	 * search course has been taken and being taking 
	 * @param con
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<StudentCourse> findTakenCourseById(Connection con, int studentId) throws Exception{
		List<StudentCourse> courseList = new ArrayList<StudentCourse>();
		String sql="SELECT t1.courseId as id, t4.courseNo as courseNo, t2.courseName as courseName, t2.credit as credit, t3.trueName as teacherName, t1.score as score FROM t_student_course t1, t_course t2, t_teacher t3, t_courselist t4 WHERE t1.courseId = t2.id AND t1.finish!=3 AND t3.id = t2.teacherId AND t4.courseName = t2.courseName AND t1.studentId ="+studentId;
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			StudentCourse takenCourse = new StudentCourse();
			takenCourse.setCourseId(rs.getInt("id"));
			takenCourse.setCourseNo(rs.getString("courseNo"));
			takenCourse.setCourseName(rs.getString("courseName"));
			takenCourse.setCredit(rs.getInt("credit"));
			takenCourse.setTeacherName(rs.getString("teacherName"));
			takenCourse.setScore(rs.getInt("score"));
			courseList.add(takenCourse);
		}
		return courseList;
		
	}
	
	public List<StudentCourse> findFinishedCourseById(Connection con, int studentId) throws Exception{
		List<StudentCourse> courseList = new ArrayList<StudentCourse>();
		String sql="SELECT t1.courseId as id, t4.courseNo as courseNo, t2.courseName as courseName, t2.credit as credit, t3.trueName as teacherName, t1.score as score FROM t_student_course t1, t_course t2, t_teacher t3, t_courselist t4 WHERE t1.courseId = t2.id AND t1.finish=1 AND t3.id = t2.teacherId AND t4.courseName = t2.courseName AND t1.studentId ="+studentId;
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			StudentCourse takenCourse = new StudentCourse();
			takenCourse.setCourseId(rs.getInt("id"));
			takenCourse.setCourseNo(rs.getString("courseNo"));
			takenCourse.setCourseName(rs.getString("courseName"));
			takenCourse.setCredit(rs.getInt("credit"));
			takenCourse.setTeacherName(rs.getString("teacherName"));
			takenCourse.setScore(rs.getInt("score"));
			courseList.add(takenCourse);
		}
		return courseList;
		
	}
	
	
	/**
	 * add studentCourse relationships
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	public int addStudentCourse(Connection con,StudentCourse sc)throws Exception{
		String sql="insert into t_student_course values(null,?,?,null,0)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, sc.getStudentId());
		pstmt.setInt(2, sc.getCourseId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * delete studentCourse relationships 
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteStudentCourse(Connection con,String id)throws Exception{
		String sql="delete from t_student_course where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		return pstmt.executeUpdate();
	}
	
	/**
	 * to judge if one course has been chosen by students
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean existCourseById(Connection con,String id)throws Exception{
		String sql="select * from t_student_course where courseId=? AND finish = 0 ";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * to judge if one student has chosen one course
	 * @param con
	 * @param stuId
	 * @return
	 * @throws Exception
	 */
	public boolean existStudentByStuId(Connection con,String stuId)throws Exception{
		String sql="select * from t_student_course where studentId=? AND finish = 0";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, stuId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * to judge if one course has its grade
	 * @param con
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public boolean hasScoreWithCourseId(Connection con,String id)throws Exception{
		String sql="select score from t_student_course where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			if(rs.getString("score")==null){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getMyCourseIdList(Connection con, Integer id)throws Exception{
		String sql="select courseId from t_student_course where studentId = "+id;
		PreparedStatement pstmt = con.prepareStatement(sql);
		List<Integer> myCourseIdList = new ArrayList<Integer>();
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()) {
			int courseId = rs.getInt("courseId");
			myCourseIdList.add(courseId);
		}
		return myCourseIdList;
		
	}
	
	public void setStudentCourseFinish(Connection con,String id,int finish) throws Exception{
		String sql="UPDATE `xsxk`.`t_student_course` SET `finish` = ? WHERE (`id` = ?);";
		PreparedStatement pstmt =con.prepareStatement(sql);
		pstmt.setInt(1, finish);
		pstmt.setString(2, id);
		pstmt.execute();
		
	}
}
