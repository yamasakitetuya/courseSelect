package com.java1234.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.java1234.dao.CourseDao;
import com.java1234.dao.StudentCourseDao;
import com.java1234.dao.StudentDao;
import com.java1234.model.Course;
import com.java1234.model.PageBean;
import com.java1234.model.Student;
import com.java1234.model.StudentCourse;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.PageUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

/**
 * student Servlet class
 * @author Administrator
 *
 */
public class StudentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DbUtil dbUtil=new DbUtil();
	private StudentDao studentDao=new StudentDao();
	private StudentCourseDao studentCourseDao=new StudentCourseDao();
	private CourseDao courseDao=new CourseDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("list".equals(action)){
			this.list(request, response);
		}else if("preSave".equals(action)){
			this.preSave(request, response);
		}else if("save".equals(action)){
			this.save(request, response);
		}else if("delete".equals(action)){
			this.delete(request, response);
		}else if("courseList".equals(action)){
			this.courseList(request, response);
		}else if("showInfo".equals(action)){
			this.showInfo(request,response);
		}else if("showScore".equals(action)){
			this.showScore(request,response);
		}else if("preSelect".equals(action)){
			this.preSelect(request,response);
		}else if("selectCourse".equals(action)){
			this.selectCourse(request,response);
		}else if("unSelectCourse".equals(action)){
			this.unSelectCourse(request, response);
		}
	}
	
	/**
	 * show student information
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void list(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session=request.getSession();
		String page=request.getParameter("page");
		String s_userName=request.getParameter("s_userName");
		Student s_student=new Student();
		if(StringUtil.isEmpty(page)){
			page="1";
			s_student.setTrueName(s_userName);
			session.setAttribute("s_student", s_student);
		}else{
			s_student=(Student) session.getAttribute("s_student");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),3);
		Connection con=null;
		try{
			con=dbUtil.getCon();
			List<Student> studentList=studentDao.studentList(con, pageBean, s_student);
			int total=studentDao.studentCount(con, s_student);
			String pageCode=PageUtil.getPagation(request.getContextPath()+"/student?action=list", total, Integer.parseInt(page), 3);
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("modeName", "student managenent ");
			request.setAttribute("studentList", studentList);
			request.setAttribute("mainPage", "student/list.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * presave and prealter
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void preSave(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String id=request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			request.setAttribute("actionName", "alter student information");
			Connection con=null;
			try{
				con=dbUtil.getCon();
				Student student=studentDao.loadStudentById(con, id);
				request.setAttribute("student", student);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			request.setAttribute("actionName", "add student information");
		}
		request.setAttribute("mainPage", "student/save.jsp");
		request.setAttribute("modeName", "student management");
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}
	
	/**
	 * add and alter
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void save(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String id=request.getParameter("id");
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String trueName=request.getParameter("trueName");
		String stuNo=request.getParameter("stuNo");
		String professional=request.getParameter("professional");
		int status =Integer.valueOf(request.getParameter("status"));
		Student student=new Student(userName,password,trueName,stuNo,professional,status);
		Connection con=null;
		try{
			con=dbUtil.getCon();
			if(StringUtil.isNotEmpty(id)){ // alter
				student.setId(Integer.parseInt(id));
				studentDao.studentUpdate(con, student);
			}else{
				studentDao.studentAdd(con, student);
			}
			response.sendRedirect("student?action=list");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * delete
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String id=request.getParameter("id");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result = new JSONObject();
			if(studentCourseDao.existStudentByStuId(con, id)){
				result.put("success", false);
			}else{
				studentDao.studentDelete(con, id);
				result.put("success", true);				
			}
			ResponseUtil.write(result, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * search student's course 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void courseList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session=request.getSession();
		User currentUser=(User) session.getAttribute("currentUser");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			List<StudentCourse> courseList=studentCourseDao.findCourseByStudentId(con, currentUser.getUserId());
			request.setAttribute("courseList", courseList);
			request.setAttribute("modeName", "check course information");
			request.setAttribute("mainPage", "student/courseList.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * check personal information
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showInfo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session=request.getSession();
		User currentUser=(User) session.getAttribute("currentUser");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			Student student=studentDao.loadStudentById(con, String.valueOf(currentUser.getUserId()));
			request.setAttribute("student", student);
			request.setAttribute("modeName", "check personal information");
			request.setAttribute("mainPage", "student/info.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * search grades
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showScore(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session=request.getSession();
		User currentUser=(User) session.getAttribute("currentUser");
		Boolean checkIfProfessionFinished = true;
		Connection con=null;
		try{
			con=dbUtil.getCon();
			//该学生在修以及修完的课程
			List<StudentCourse> courseList=studentCourseDao.findTakenCourseById(con, currentUser.getUserId());
			List<StudentCourse> finishedCourseList = studentCourseDao.findFinishedCourseById(con, currentUser.getUserId());
			List<String> finishedCourseNameList = new ArrayList<String>();
			//该学生已修和在修的课程名集合
			List<String> courseNameList = new ArrayList<String>();
			//未修的专业课程
			List<Course> unfinishedProfessionalCourseList = new ArrayList<Course>();
			List<Course> allProfessionalCourseList=studentCourseDao.findAllProfessionalCourseList(con, currentUser.getUserId());
			for(StudentCourse sc :courseList) {
				courseNameList.add(sc.getCourseName());
			}
			for(StudentCourse sc:finishedCourseList) {
				finishedCourseNameList.add(sc.getCourseName());
			}
			for(Course c:allProfessionalCourseList) {
				if(!finishedCourseNameList.contains(c.getCourseName())) {
					checkIfProfessionFinished = false;
					break;
				}
			}
			for(Course c:allProfessionalCourseList) {
				if(!courseNameList.contains(c.getCourseName())) {
					unfinishedProfessionalCourseList.add(c);
					
					}	
			}
			
			
			int totalCredit = studentDao.findTotaldCredit(con, currentUser.getUserId());
			//未完成的专业课
			request.setAttribute("unfinishedProfessionalCourseList", unfinishedProfessionalCourseList);
			request.setAttribute("checkIfProfessionFinished", checkIfProfessionFinished);
			request.setAttribute("courseList", courseList);
			request.setAttribute("totalCredit", totalCredit);
			request.setAttribute("modeName", "search grades");
			request.setAttribute("mainPage", "student/showScore.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * preselect 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void preSelect(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session=request.getSession();
		User currentUser=(User) session.getAttribute("currentUser");
		String s_courseName = request.getParameter("s_courseName");
		String s_profession = request.getParameter("s_profession");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			// select all courses 
			List<Course> courseList=courseDao.courseList(con, null, null);
			// current course of a student 
			List<StudentCourse> myCourseList=studentCourseDao.findCourseByStudentId(con, currentUser.getUserId());
			//all required professional course 
			List<Course> professionCourseList= studentCourseDao.findOpenedCourseByStudentProfession(con, currentUser.getUserId());
			//professional course which could be chosen 
			List<Course> professionCourseListNow=new ArrayList<Course>();
			
			List<StudentCourse> myTakenCourseList = studentCourseDao.findTakenCourseById(con, currentUser.getUserId());
			
			//List<Integer> myCourseIdList = studentCourseDao.getMyCourseIdList(con, currentUser.getUserId());
			
			List<Integer> professionCourseIdList = new ArrayList<Integer>();
			
			List<Integer> myTakenCourseIdList = new ArrayList<Integer>();
			
			for(StudentCourse c:myTakenCourseList) {
				myTakenCourseIdList.add(c.getCourseId());
				
			}
			for(Course cp:professionCourseList) {
				professionCourseIdList.add(cp.getId());
			}
			
			//get profession course at the current time
			for(Course cp:professionCourseList) {
				if(!myTakenCourseIdList.contains(cp.getId())) {
					professionCourseListNow.add(cp);
				}
				
			}
			// course could be chosen 
			List<Course> selectCourseList=new ArrayList<Course>();
			for(Course c:courseList){
				// course which is not in the user current course id 
				if(!myTakenCourseIdList.contains(c.getId())){
					if(!professionCourseIdList.contains(c.getId())) {
						selectCourseList.add(c);
					}
					
				}
			}
			if(StringUtil.isNotEmpty(s_profession)) {
				Iterator<Course> iterator = selectCourseList.iterator();
				while(iterator.hasNext()) {
					Course c = iterator.next();
					if(c.getCourseNo().indexOf(s_profession)==-1) {
						iterator.remove();
					}
					
				}
			}
			
			if(StringUtil.isNotEmpty(s_courseName)){
				Iterator<Course> iterator = selectCourseList.iterator();
				while(iterator.hasNext()) {
					Course c = iterator.next();
					if(c.getCourseName().indexOf(s_courseName)==-1) {
						iterator.remove();
					}	
				}
			}
			request.setAttribute("s_courseName", s_courseName);
			request.setAttribute("professionCourseListNow", professionCourseListNow);
			request.setAttribute("selectCourseList", selectCourseList); // courses could be chosen
			request.setAttribute("myCourseList", myCourseList); // courses have been chosen 
			request.setAttribute("modeName", "course selecting");
			request.setAttribute("mainPage", "student/selectCourse.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * course select 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void selectCourse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session=request.getSession();
		User currentUser=(User) session.getAttribute("currentUser");
		String courseIds=request.getParameter("courseIds"); // get id of current chosen courses 
		String cIds[]=courseIds.split(",");
		Connection con=null;
		boolean a=true;
		try{
			con=dbUtil.getCon();
			for(int i=0;i<cIds.length;i++){
				StudentCourse sc=new StudentCourse();
				sc.setStudentId(currentUser.getUserId());
				sc.setCourseId(Integer.parseInt(cIds[i]));
				List<StudentCourse> finishedCourseList = studentCourseDao.findFinishedCourseById(con, currentUser.getUserId());
				Course selectedCourse = courseDao.loadCourseById(con, cIds[i]);
				List<Course> firstLessonList = courseDao.loadFirstLessonByCourseNo(con, selectedCourse.getCourseNo());
				List<String> finishedCourseNoList = new ArrayList<String>();
				for(StudentCourse c:finishedCourseList) {
					finishedCourseNoList.add(c.getCourseNo());
				}
				for(Course c:firstLessonList) {
					if(!finishedCourseNoList.contains(c.getCourseNo())) {
						a=false;
						break;
					}
				}
				//先修课验证模块
				if(a) {
					studentCourseDao.addStudentCourse(con, sc);
					JSONObject result = new JSONObject();
					result.put("success", true);
					ResponseUtil.write(result, response);
				}else {
					JSONObject result = new JSONObject();
					result.put("errorMsg", "存在未修的先修课");
					ResponseUtil.write(result, response);
					
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * quit courses 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void unSelectCourse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String scIds=request.getParameter("scIds"); // get id of current chosen courses 
		String scIdsStr[]=scIds.split(",");
		Connection con=null;
		boolean flag=true;
		try{
			con=dbUtil.getCon();
			JSONObject result = new JSONObject();
			for(int i=0;i<scIdsStr.length;i++){
				if(studentCourseDao.hasScoreWithCourseId(con, scIdsStr[i])){
					flag=false;
				}else{
					studentCourseDao.deleteStudentCourse(con, scIdsStr[i]);					
				}
			}
			result.put("success", flag);
			ResponseUtil.write(result, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * student register with email address
	 */
	
	
}
