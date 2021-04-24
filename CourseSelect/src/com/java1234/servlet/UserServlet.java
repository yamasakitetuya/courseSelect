package com.java1234.servlet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java1234.dao.ManagerDao;
import com.java1234.dao.StudentDao;
import com.java1234.dao.TeacherDao;
import com.java1234.model.User;
import com.java1234.util.CodeUtil;
import com.java1234.util.DbUtil;
import com.java1234.util.MailUtil;
import com.java1234.util.StringUtil;

/**
 * user Servlet class
 * @author Administrator
 *
 */
public class UserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DbUtil dbUtil=new DbUtil();
	private ManagerDao managerDao=new ManagerDao();
	private TeacherDao teacherDao=new TeacherDao();
	private StudentDao studentDao=new StudentDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action"); // get the action user start
		if("login".equals(action)){
			this.login(request, response);
		}else if("logout".equals(action)){
			this.logout(request, response);
		}else if("register".equals(action)) {
			this.register(request,response);
		}else if("certificate".equals(action)){
			try {
				this.certificate(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("findPassword".equals(action)) {
			this.findPassword(request, response);
		}else if("sendPassword".equals(action)) {
			try {
				this.sendPassword(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * user login
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String userType=request.getParameter("userType");
		String error="";
		if(StringUtil.isEmpty(userName)){
			error="username no empty";
		}else if(StringUtil.isEmpty(password)){
			error="password no empty";
		}else if(StringUtil.isEmpty(userType)){
			error="identification no empty";
		}
		User user=new User(userName,password,userType);
		if(StringUtil.isNotEmpty(error)){
			request.setAttribute("user", user);
			request.setAttribute("error", error);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		Connection con=null;
		User currentUser=null;
		try{
			con=dbUtil.getCon();
			if("manager".equals(userType)){
				currentUser=managerDao.login(con, user);
			}else if("teacher".equals(userType)){
				currentUser=teacherDao.login(con, user);
			}else if("student".equals(userType)){
				currentUser=studentDao.login(con, user);
			}
			
			if(currentUser==null||currentUser.getUserType()=="unidentified"){
				error="登录无效";
				request.setAttribute("user", user);
				request.setAttribute("userName", user.getUserName());
				request.setAttribute("userType", user.getUserType());
				request.setAttribute("error", error);
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}else{
				HttpSession session=request.getSession();
				session.setAttribute("currentUser", currentUser);
				response.sendRedirect("main.jsp");
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
	 * safe logout
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect("login.jsp");
	}
	
	/**
	 * register
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName=request.getParameter("userName");
		String uid=request.getParameter("uid");
		String registerRole=request.getParameter("registerRole");;
		Connection con = null;
		try {
			if(StringUtil.isEmpty(userName)) {
				//when there is no userName(email) sent back
				User registerUser = new User("registerRole","***","registerRole");
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", registerUser);
				session.setAttribute("registerRole", registerRole);
		        response.sendRedirect("registe.jsp");
			}else {
				//when there is an email(userName) sent back
				if("student".equals(registerRole)) {
					//search if there is such user in t_student
					con=dbUtil.getCon();
					studentDao.checkExistByStuId(con, uid, userName);
					response.sendRedirect("login.jsp");
				}else {
					//search if there is such user in t_teacher	
					con=dbUtil.getCon();
					teacherDao.checkExistByTeaId(con, uid, userName);
					response.sendRedirect("login.jsp");
				}
			}
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
	
	private void findPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String registerRole=request.getParameter("registerRole");
		try {		
			User registerUser = new User("registerRole","***","registerRole");
			HttpSession session = request.getSession();
			session.setAttribute("currentUser", registerUser);
			session.setAttribute("registerRole", registerRole);
		    response.sendRedirect("findPassword.jsp");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	private void certificate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MessagingException, GeneralSecurityException {
		String userName = request.getParameter("userName");
		String uid = request.getParameter("uid");
		String registerRole = request.getParameter("registerRole");
		MailUtil mail = new MailUtil(uid,userName,registerRole);
		mail.send();
		response.sendRedirect("login.jsp");
	
	}
	
	private void sendPassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userName = request.getParameter("userName");
		String uid = request.getParameter("uid");
		String registerRole = request.getParameter("registerRole");
		String pwd = null;
		Connection con = dbUtil.getCon();
		if("student".equals(registerRole)) {
			pwd = studentDao.findPassword(con, userName, uid);
		}else {
			pwd = teacherDao.findPassword(con, userName, uid);
		}
		MailUtil mail = new MailUtil();
		mail.setTo(userName);
		mail.send(pwd);
		response.sendRedirect("login.jsp");
	}
	

	
	

}
