package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.PageBean;
import com.java1234.model.Teacher;
import com.java1234.model.User;
import com.java1234.util.StringUtil;

/**
 * teacher Dao class
 * @author Administrator
 *
 */
public class TeacherDao {
	
	
	/**
	 * 
	 * @param con
	 * @param teaNo
	 * @param userName
	 * @throws Exception
	 */
	public void checkExistByTeaId(Connection con, String teaNo, String userName)throws Exception{
		String sql ="UPDATE `xsxk`.`t_teacher` SET `userName` = ? , `status` = 1 WHERE (`teaNo` = ?) AND (`status`= 0);";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		pstmt.setString(2, teaNo);
		pstmt.executeUpdate();
	}

	/**
	 * teacher login
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User login(Connection con,User user)throws Exception{
		User resultUser=null;
		String sql="select * from t_teacher where userName=? and password=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			resultUser=new User();
			if(rs.getInt("status")==1){
				resultUser.setUserId(rs.getInt("id"));
				resultUser.setUserName(rs.getString("userName"));
				resultUser.setPassword(rs.getString("password"));
				resultUser.setTrueName(rs.getString("trueName"));
				resultUser.setUserType("教师");
			}else {
				resultUser.setUserType("unidentified");
			}
			
			
		}
		return resultUser;
	}
	
	/**
	 * search teacher information
	 * @param con
	 * @param pageBean
	 * @param s_teacher
	 * @return
	 * @throws Exception
	 */
	public List<Teacher> teacherList(Connection con,PageBean pageBean,Teacher s_teacher)throws Exception{
		List<Teacher> teacherList=new ArrayList<Teacher>();
		StringBuffer sb=new StringBuffer("select * from t_teacher ");
		if(s_teacher!=null){
			if( StringUtil.isNotEmpty(s_teacher.getTrueName())){
				sb.append(" and trueName like '%"+s_teacher.getTrueName()+"%'");
			}			
		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Teacher teacher=new Teacher();
			teacher.setId(rs.getInt("id"));
			teacher.setUserName(rs.getString("userName"));
			teacher.setPassword(rs.getString("password"));
			teacher.setTrueName(rs.getString("trueName"));
			teacher.setTitle(rs.getString("title"));
			teacher.setTeaNo(rs.getString("teaNo"));
			teacher.setStatus(rs.getInt("status"));
			teacherList.add(teacher);
		}
		return teacherList;
	}
	
	/**
	 * count teacher number 
	 * @param con
	 * @param s_teacher
	 * @return
	 * @throws Exception
	 */
	public int teacherCount(Connection con,Teacher s_teacher)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_teacher ");
		if(s_teacher!=null){
			if(StringUtil.isNotEmpty(s_teacher.getTrueName())){
				sb.append(" and trueName like '%"+s_teacher.getTrueName()+"%'");
			}			
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	/**
	 * add one teacher
	 * @param con
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	public int teacherAdd(Connection con,Teacher teacher)throws Exception{
		String sql="insert into t_teacher values(null,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, teacher.getUserName());
		pstmt.setString(2, teacher.getPassword());
		pstmt.setString(3, teacher.getTrueName());
		pstmt.setString(4, teacher.getTitle());
		pstmt.setInt(5, teacher.getStatus());
		pstmt.setString(6, teacher.getTeaNo());
		
		return pstmt.executeUpdate();
	}
	
	/**
	 * update one teacher's information 
	 * @param con
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	public int teacherUpdate(Connection con,Teacher teacher)throws Exception{
		String sql="update t_teacher set userName=?,password=?,trueName=?,title=?,status=?,teaNo=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, teacher.getUserName());
		pstmt.setString(2, teacher.getPassword());
		pstmt.setString(3, teacher.getTrueName());
		pstmt.setString(4, teacher.getTitle());
		pstmt.setInt(5, teacher.getStatus());
		pstmt.setString(6, teacher.getTeaNo());
		pstmt.setInt(7, teacher.getId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * delete one teacher
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int teacherDelete(Connection con,String id)throws Exception{
		String sql="delete from t_teacher where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		return pstmt.executeUpdate();
	}
	
	/**
	 * search teacher by id 
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Teacher loadTeacherById(Connection con,String id)throws Exception{
		String sql="select * from t_teacher where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs=pstmt.executeQuery();
		Teacher teacher=new Teacher();
		while(rs.next()){
			teacher.setId(rs.getInt("id"));
			teacher.setUserName(rs.getString("userName"));
			teacher.setPassword(rs.getString("password"));
			teacher.setTrueName(rs.getString("trueName"));
			teacher.setTitle(rs.getString("title"));
			teacher.setTeaNo(rs.getString("teaNo"));
			teacher.setStatus(rs.getInt("status"));
		}
		return teacher;
	}
	public String findPassword(Connection con, String userName,String stuNo) throws Exception{
		String sql = "SELECT password FROM t_teacher WHERE userName=? AND teaNo=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		String pwd = null;
		pstmt.setString(1, userName);
		pstmt.setString(2, stuNo);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			pwd = rs.getString("password");
		}
		return pwd;
	}
	
}
