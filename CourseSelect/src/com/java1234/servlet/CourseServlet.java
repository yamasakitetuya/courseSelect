package com.java1234.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.java1234.dao.CourseDao;
import com.java1234.dao.StudentCourseDao;
import com.java1234.dao.TeacherDao;
import com.java1234.model.Course;
import com.java1234.model.PageBean;
import com.java1234.model.Teacher;
import com.java1234.util.DbUtil;
import com.java1234.util.PageUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

/**
 * course Servlet class
 * 
 * @author Administrator
 * 
 */
public class CourseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DbUtil dbUtil = new DbUtil();
	private CourseDao courseDao = new CourseDao();
	private TeacherDao teacherDao = new TeacherDao();
	private StudentCourseDao studentCourseDao=new StudentCourseDao();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if ("list".equals(action)) {
			this.list(request, response);
		} else if ("preSave".equals(action)) {
			this.preSave(request, response);
		} else if ("save".equals(action)) {
			this.save(request, response);
		} else if ("delete".equals(action)) {
			this.delete(request, response);
		} else if("finishCourse".equals(action)) {
			this.finishCourse(request, response);
		}else if("foundationCourseList".equals(action)) {
			this.foundationCourseList(request, response);
		}else if ("preSaveFoundationCourse".equals(action)) {
			this.preSaveFoundationCourse(request, response);
		}else if("saveFoundationCourse".equals(action)) {
			this.saveFoundationCourse(request, response);
		}else if("deleteFoundationCourse".equals(action)) {
			this.deleteFoundationCourse(request, response);
		}else if("firstLessonList".equals(action)) {
			this.firstLessonList(request, response);
		}else if("preSaveFirstLesson".equals(action)) {
			this.preSaveFirstLesson(request, response);
		}else if("saveFirstLesson".equals(action)) {
			this.saveFirstLesson(request, response);
		}else if("deleteFirstLesson".equals(action)) {
			this.deleteFirstLesson(request, response);
			
		}
	}
	
	private void foundationCourseList(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		List<Course> foundationCourseList = new ArrayList<Course>();
		String s_courseName = request.getParameter("s_courseName");
		String page =request.getParameter("page");
		HttpSession session = request.getSession();
		if (StringUtil.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_courseName", s_courseName);
		} else {
			s_courseName = (String) session.getAttribute("s_courseName");
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		Connection con=null;
		try {
			con=dbUtil.getCon();
			foundationCourseList = courseDao.foundationCourseList(con,s_courseName,pageBean);
			int total = courseDao.foundationCourseCount(con, s_courseName);
			String pageCode = PageUtil.getPagation(request.getContextPath()
					+ "/course?action=foundationCourseList", total, Integer.parseInt(page), 3);
			request.setAttribute("pageCode", pageCode);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("modeName", "foundational course management");
		request.setAttribute("mainPage", "course/foundationCourseList.jsp");
		request.setAttribute("foundationCourseList", foundationCourseList);
		request.getRequestDispatcher("main.jsp").forward(request, response);
		
	}

	/**
	 * show course information
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String page = request.getParameter("page");
		String s_courseName = request.getParameter("s_courseName");
		Course s_course = new Course();
		if (StringUtil.isEmpty(page)) {
			page = "1";
			s_course.setCourseName(s_courseName);
			session.setAttribute("s_course", s_course);
		} else {
			s_course = (Course) session.getAttribute("s_course");
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			//搜索课程,提供了排课表内的id
			List<Course> courseList = courseDao.courseList(con, pageBean,
					s_course);
			int total = courseDao.courseCount(con, s_course);
			String pageCode = PageUtil.getPagation(request.getContextPath()
					+ "/course?action=list", total, Integer.parseInt(page), 3);
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("modeName", "course management");
			request.setAttribute("courseList", courseList);
			request.setAttribute("mainPage", "course/list.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void preSave(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if (StringUtil.isNotEmpty(id)) {
				request.setAttribute("actionName", "alter course");
				Course course = courseDao.loadCourseById(con, id);
				request.setAttribute("course", course);
			} else {
				request.setAttribute("actionName", "add course");
			}
			// search all teachers 
			List<Teacher> teacherList=teacherDao.teacherList(con, null, null);
			request.setAttribute("teacherList", teacherList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("mainPage", "course/save.jsp");
		request.setAttribute("modeName", "course management");
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void preSaveFoundationCourse(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");//for update
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if (StringUtil.isNotEmpty(id)) {
				request.setAttribute("actionName", "alter course");
                //修改基础课程信息模块
			    Course course = courseDao.loadFoundationCourseById(con, id);
			    //Course firstLesson = courseDao.loadFirstLessonByCourseNo(con, course.getCourseNo());
				request.setAttribute("course", course);
				//request.setAttribute("firstLesson", firstLesson);
			} else {
				request.setAttribute("actionName", "add course");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("mainPage", "course/foundationCourseSave.jsp");
		request.setAttribute("modeName", "course management");
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}
	
	private void preSaveFirstLesson(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String courseNo = request.getParameter("courseNo");//for add
		Connection con = null;
		try {
			con = dbUtil.getCon();
			request.setAttribute("actionName", "add first lesson");
			request.setAttribute("courseNo", courseNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("mainPage", "course/firstLessonSave.jsp");
		request.setAttribute("modeName", "course management");
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}
	
	private void firstLessonList(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String courseNo = request.getParameter("courseNo");
		List<Course> firstLessonList = new ArrayList<Course>();
		Connection con = null;
		try {
			con = dbUtil.getCon();
			firstLessonList = courseDao.loadFirstLessonByCourseNo(con, courseNo);
			request.setAttribute("firstLessonList", firstLessonList);
			request.setAttribute("courseNo", courseNo);
			request.setAttribute("modeName", "first lesson management");
			request.setAttribute("mainPage", "course/firstLessonList.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			}catch(Exception e) {
				e.printStackTrace();
				
			}
		}
	}

	/**
	 * add and alter
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String courseName = request.getParameter("courseName");
		String credit = request.getParameter("credit");
		String teacherId = request.getParameter("teacherId");
		String courseTime = request.getParameter("courseTime");
		String classroom = request.getParameter("classroom");
		Course course = new Course(courseName, Integer.parseInt(credit),
				Integer.parseInt(teacherId));
		course.setCourseTime(courseTime);
		course.setClassroom(classroom);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if (StringUtil.isNotEmpty(id)) { // alter
				course.setId(Integer.parseInt(id));
				courseDao.courseUpdate(con, course);
			} else {
				courseDao.courseAdd(con, course);
			}
			response.sendRedirect("course?action=list");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void saveFoundationCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String courseName = request.getParameter("courseName");
		String courseNo = request.getParameter("courseNo");
		Course course = new Course();
		course.setCourseName(courseName);
		course.setCourseNo(courseNo);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if (StringUtil.isNotEmpty(id)) { // alter
				course.setId(Integer.parseInt(id));
				courseDao.foundationCourseUpdate(con, course);
			} else {
				courseDao.addFoundationCourse(con, course);
			}
			response.sendRedirect("course?action=foundationCourseList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void saveFirstLesson(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String courseNo = request.getParameter("courseNo");
		String firstLessonNo = request.getParameter("firstLessonNo");
		Connection con = null;
		try {
			 con = dbUtil.getCon();
			courseDao.addFirstLesson(con,courseNo,firstLessonNo);
			response.sendRedirect("course?action=firstLessonList&courseNo="+courseNo);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
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
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println(id);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			if(studentCourseDao.existCourseById(con, id)){
				result.put("success", false);
			}else{
				result.put("success", true);
				courseDao.courseDelete(con, id);
				
			}
			ResponseUtil.write(result, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void deleteFoundationCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			if(!courseDao.existFoundationCourseById(con, id)){
				result.put("success", false);
			}else{
				result.put("success", true);
				Course c = courseDao.loadFoundationCourseById(con,id);
				courseDao.deleteFirstLesson(con, c.getCourseNo());
				courseDao.deleteFoundationCourse(con, id);				
			}
			ResponseUtil.write(result, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	private void deleteFirstLesson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseNo = request.getParameter("courseNo");
		String firstLessonNo = request.getParameter("firstLessonNo");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if(!courseDao.existFirstLesson(con, courseNo,firstLessonNo)){
				request.setAttribute("result", "没有相关记录");
			}else{
				request.setAttribute("result", "已删除");
				courseDao.deleteFirstLesson(con, courseNo,firstLessonNo);				
			}
			response.sendRedirect("course?action=firstLessonList&courseNo="+courseNo);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	
	
	private void finishCourse(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String id = request.getParameter("id");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			if(courseDao.existStudentWithCourseId(con, id)){
				result.put("errorInfo", "无法完成，请确认没有在修学生");
			}else {
				result.put("success", true);
				courseDao.finishCourse(con, id);
			}
			ResponseUtil.write(result, response);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
