package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.PageBean;
import com.java1234.model.Course;
import com.java1234.util.StringUtil;

/**
 * course Dao class
 * @author Administrator
 *
 */
public class CourseDao {

	/**
	 * search course information
	 * @param con
	 * @param pageBean
	 * @param s_course
	 * @return
	 * @throws Exception
	 */
	public List<Course> courseList(Connection con,PageBean pageBean,Course s_course)throws Exception{
		List<Course> courseList=new ArrayList<Course>();
		StringBuffer sb=new StringBuffer("select t1.id as id, t3.courseNo as courseNo, t3.courseName as courseName, t1.credit as credit, t1.teacherId as teacherId, t2.trueName as trueName, t1.courseTime as courseTime, t1.classroom as classroom from t_course t1,t_teacher t2, t_courselist t3 where t1.teacherId=t2.id and t1.courseName = t3.courseName and t1.finish = 1 ");
		if(s_course!=null){
			if(StringUtil.isNotEmpty(s_course.getCourseName())){
				sb.append(" and t1.courseName like '%"+s_course.getCourseName()+"%'");
			}
			if(s_course.getTeacherId()!=null){
				sb.append(" and t1.teacherId="+s_course.getTeacherId());
			}
		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Course course=new Course();
			course.setId(rs.getInt("id"));
			course.setCourseNo(rs.getString("courseNo"));
			course.setCourseName(rs.getString("courseName"));
			course.setCredit(rs.getInt("credit"));
			course.setTeacherId(rs.getInt("teacherId"));
			course.setTeacherName(rs.getString("trueName"));
			course.setCourseTime(rs.getString("courseTime"));
			course.setClassroom(rs.getString("classroom"));
			courseList.add(course);
		}
		return courseList;
	}
	
	public int foundationCourseCount(Connection con, String s_courseName) throws Exception{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_courselist");
		if(StringUtil.isNotEmpty(s_courseName)) {
			sb.append("and courseName like '%"+s_courseName+"%'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		}else {
			return 0;
		}
	}
	
	/**
	 * count course number 
	 * @param con
	 * @param s_course
	 * @return
	 * @throws Exception
	 */
	public int courseCount(Connection con,Course s_course)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_course t1,t_teacher t2 where t1.teacherId=t2.id and t1.finish=1");
		if(s_course!=null){
			if( StringUtil.isNotEmpty(s_course.getCourseName())){
				sb.append(" and t1.courseName like '%"+s_course.getCourseName()+"%'");
			}			
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	/**
	 *  add course
	 * @param con
	 * @param course
	 * @return
	 * @throws Exception
	 */
	public int courseAdd(Connection con,Course course)throws Exception{
		String sql="insert into t_course values(null,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, course.getCourseName());
		pstmt.setInt(2, course.getCredit());
		pstmt.setInt(3, course.getTeacherId());
		pstmt.setString(4, course.getCourseTime());
		pstmt.setInt(5, 1);
		pstmt.setString(6, course.getClassroom());
		return pstmt.executeUpdate();
	}
	
	public void foundationCourseUpdate(Connection con,Course course)throws Exception{
		String sql ="UPDATE `xsxk`.`t_courselist` SET `courseName` = ?, `courseNo` = ? WHERE (`id` = ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, course.getCourseName());
		pstmt.setString(2, course.getCourseNo());
		pstmt.setInt(3, course.getId());
		pstmt.execute();
	}
	
	/**
	 * update course 
	 * @param con
	 * @param course
	 * @return
	 * @throws Exception
	 */
	public int courseUpdate(Connection con,Course course)throws Exception{
		String sql="update t_course set courseName=?,credit=?,teacherId=?,courseTime=?,classroom=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, course.getCourseName());
		pstmt.setInt(2, course.getCredit());
		pstmt.setInt(3, course.getTeacherId());
		pstmt.setString(4, course.getCourseTime());
		pstmt.setString(5, course.getClassroom());
		pstmt.setInt(6, course.getId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * delete course 
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int courseDelete(Connection con,String id)throws Exception{
		String sql="delete from t_course where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		return pstmt.executeUpdate();
	}
	
	public void deleteFoundationCourse(Connection con,String id)throws Exception{
		String sql="delete from t_courselist where id =?";
		PreparedStatement pstmt =con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.execute();
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Course loadCourseById(Connection con,String id)throws Exception{
		String sql="select * from t_course t1, t_courselist t2 where t1.id=? AND t1.courseName = t2.courseName";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs=pstmt.executeQuery();
		Course course=new Course();
		while(rs.next()){
			course.setId(rs.getInt("id"));
			course.setCourseName(rs.getString("courseName"));
			course.setCourseNo(rs.getString("courseNo"));
			course.setCredit(rs.getInt("credit"));
			course.setTeacherId(rs.getInt("teacherId"));
			course.setCourseTime(rs.getString("courseTime"));
			course.setClassroom(rs.getString("classroom"));
		}
		return course;
	}
	
	/**
	 * 
	 * @param con
	 * @param CourseNo
	 * @return
	 * @throws Exception
	 */
	public List<Course> loadFirstLessonByCourseNo(Connection con, String courseNo) throws Exception{
		List<Course> firstLessonList = new ArrayList<Course>();
		String sql = "SELECT * FROM t_courselist t1, t_firstlesson t2 WHERE t2.courseNo=? AND t2.firstLessonNo = t1.courseNo";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1,courseNo);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Course c = new Course();
			c.setCourseName(rs.getString("courseName"));
			c.setCourseNo(rs.getString("courseNo"));
			firstLessonList.add(c);
			System.out.println(c.getCourseName());
		}
		return firstLessonList;
		
	}
	
	
	
	/**
	 * 
	 * @param con
	 * @param teacherId
	 * @return
	 * @throws Exception
	 */
	public boolean existCourseWithTeacherId(Connection con,String teacherId)throws Exception{
		String sql="select * from t_course where teacherId=? AND finish=1";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, teacherId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean existStudentWithCourseId(Connection con,String courseId)throws Exception {
		String sql="SELECT * from t_student_course WHERE courseId=? AND finish=0";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, courseId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean existFoundationCourseById(Connection con,String courseId)throws Exception{
		String sql="SELECT * from t_courselist WHERE id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, courseId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void finishCourse(Connection con,String courseId) throws Exception{
		String sql="UPDATE `xsxk`.`t_course` SET `finish` = '0' WHERE (`id` = ?);";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, courseId);
		pstmt.executeQuery();
	}
	
	public List<Course> foundationCourseList(Connection con, String s_courseName,PageBean pageBean) throws Exception{
		List<Course> courseList = new ArrayList<Course>();
		StringBuffer sb = new StringBuffer("SELECT * FROM xsxk.t_courselist");
		if(StringUtil.isNotEmpty(s_courseName)) {
			sb.append(" WHERE courseName like '%"+s_courseName+"%'");
		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt;
		pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Course c = new Course();
			c.setId(rs.getInt("id"));
			c.setCourseNo(rs.getString("courseNo"));
			c.setCourseName(rs.getString("courseName"));
			courseList.add(c);
		}
		return courseList;
			
	}
	
	public void addFoundationCourse(Connection con,Course course) throws Exception {
		String sql="INSERT INTO `xsxk`.`t_courselist` (`courseName`, `courseNo`) VALUES (?, ?);";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, course.getCourseName());
		pstmt.setString(2, course.getCourseNo());
		pstmt.execute();
		
	}
	
	public void addFirstLesson(Connection con,String courseNo,String firstLessonNo) throws Exception{
		String sql = "INSERT INTO `xsxk`.`t_firstlesson` (`courseNo`, `firstLessonNo`) VALUES (?, ?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, courseNo);
		pstmt.setString(2, firstLessonNo);
		pstmt.execute();
	}
	
	public Course loadFoundationCourseById(Connection con, String id) throws Exception{
		String sql="SELECT * FROM t_courselist WHERE id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		Course c = new Course();
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			c.setId(rs.getInt("id"));
			c.setCourseName(rs.getString("courseName"));
			c.setCourseNo(rs.getString("courseNo"));
		}
		return c;
		
	}
	
	public boolean existFirstLesson(Connection con,String courseNo, String firstLessonNo) throws Exception{
		String sql="SELECT * from t_firstlesson WHERE courseNo=? AND firstLessonNo=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, courseNo);
		pstmt.setString(2, firstLessonNo);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void deleteFirstLesson(Connection con, String courseNo,String firstLessonNo )throws Exception {
		String sql ="DELETE FROM `xsxk`.`t_firstlesson` WHERE (`courseNo` = ? AND `firstLessonNo`=?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, courseNo);
		pstmt.setString(2, firstLessonNo);
		pstmt.execute();
	}
	
	public void deleteFirstLesson(Connection con, String courseNo) throws Exception{
		String sql ="DELETE FROM `xsxk`.`t_firstlesson` WHERE `courseNo` = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, courseNo);
		pstmt.execute();
		
	}
	
	
}
