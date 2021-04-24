<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- for teacher -->

<script type="text/javascript">
function finishCourse(id){
	if(confirm("确认已完成教学吗？")){
		$.post("${pageContext.request.contextPath}/course?action=finishCourse",{id:id},
			function(result){
				var obj = JSON.parse(result);
				if(obj.success){
					alert("已完成!");
					window.location.href="${pageContext.request.contextPath}/teacher?action=showCourse";
				}else{
					alert(obj.errorInfo);
				}
			}
		);
	}
}

</script>
<div>
	<table class="table table-hover  table-bordered table-striped" style="margin-bottom: 0px;">
		<tr>
		  	<th>序号</th>
		  	<th>课号</th>
		  	<th>课程名称</th>
		  	<th>学分</th>
		  	<th>授课老师</th>
		  	<th>授课时间</th>
		  	<th>操作</th>
		  </tr>
		  <c:forEach var="course" items="${courseList }" varStatus="status">
		  	<tr>
		  	    <td>${status.index+1 }</td>
		  	    <td>${course.courseNo }</td>
		  		<td>${course.courseName }</td>
		  		<td>${course.credit }</td>
		  		<td>${course.teacherName }</td>
		  		<td>${course.courseTime }</td>
		  		<td>
		  			<button type="button" class="btn btn-info btn-xs" onclick="finishCourse(${course.id })">完成教学</button>&nbsp;
		  		</td>
		  	</tr>
		  </c:forEach>
	</table>
</div>



