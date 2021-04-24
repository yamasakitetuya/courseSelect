<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row search" >
  <div class="col-md-6">
  
  </div>
  <!--添加功能  -->
  <div class="col-md-6" >
    <button type="button"  style="float: right;" class="btn btn-primary" onclick="javascript:window.location.href='${pageContext.request.contextPath}/course?action=preSaveFirstLesson&courseNo=${courseNo }'">添加</button>
  </div>
</div>
<div>
	<table class="table table-hover  table-bordered table-striped" style="margin-bottom: 0px;">
		<tr>
		  	<th>序号</th>
		  	<th>课号</th>
		  	<th>课程名称</th>
		  	<th>操作</th>
		  </tr>
		  <c:forEach var="course" items="${firstLessonList }" varStatus="status">
		  	<tr >
		  	    <td>${status.index+1 }</td>
		  		<td>${course.courseNo }</td>
		  		<td>${course.courseName }</td>
		  		<td>
		  			<button type="button" class="btn btn-danger btn-xs"  onclick="javascript:window.location.href='${pageContext.request.contextPath}/course?action=deleteFirstLesson&courseNo=${courseNo }&firstLessonNo=${course.courseNo }'">删除</button>
		  		</td>
		  	</tr>
		  </c:forEach>
	</table>
	<nav >
		<ul class="pagination">
			${pageCode }
		</ul>
	</nav>
</div>