<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(document).ready(function(){
		var currentCredit=0;
		var totalCredit=parseInt($("td#totalCredit").text());
		var checkIfProfessionFinished = ${checkIfProfessionFinished};
		$("td.1").each(function(){
			currentCredit+=parseInt($(this).text());
		});
		$("td#currentCredit").text(currentCredit);
		if(totalCredit<=currentCredit && checkIfProfessionFinished==true){
			$("td#professionStatus").text("修了");
		}else{
			$("td#professionStatus").text("在修");
		}
		
	});
</script>

<div class="panel panel-default">
	<div class="panel-heading">专业完成情况</div>
	<table class="table table-hover  table-bordered table-striped" style="margin-bottom: 0px;">
		<tr>
		  	<th>序号</th>
		  	<th>课号</th>
		  	<th>课程名称</th>
		  	<th>学分</th>
		  	<th>授课老师</th>
		  	<th>成绩</th>
		  </tr>
		  <c:forEach var="course" items="${courseList }" varStatus="status">
		  	<tr>
		  	    <td>${status.index+1 }</td>
		  	    <td>${course.courseNo }</td>
		  		<td>${course.courseName }</td>
		  		<c:if test="${course.score ==0}">
		  			<td>${course.credit }</td>
		  		</c:if>
		  		<c:if test="${course.score !=0}">
		  			<td class="1">${course.credit }</td>
		  		</c:if>
		  		
		  		<td>${course.teacherName }</td>
		  		<c:if test="${course.score ==0}">
		  			<td>老师暂未打分</td>
		  		</c:if>
		  		<c:if test="${course.score !=0}">
		  			<td>${course.score}分</td>
		  		</c:if>
		  	</tr>
		  </c:forEach>
		  <tr>
		  	<td>总要求学分</td>
		  	<td id="totalCredit">${totalCredit }</td>
		  	<td>现修得学分</td>
		  	<td id="currentCredit"></td>
		  	<td>专业完成状态</td>
		  	<td id="professionStatus"></td>
		  	
		  </tr>
	</table>
</div>
<hr/>

<div class="panel panel-default">
	<div class="panel-heading">尚未完成的专业科目</div>
	<table class="table table-hover table-bordered table-striped" style="margin-bottom: 0px">
		<tr>
		  	<th>序号</th>
		  	<th>课号</th>
		  	<th>课程名称</th>
		</tr>
		<c:forEach var="course" items="${unfinishedProfessionalCourseList }" varStatus="status">
		  	<tr>
		  	    <td>${status.index+1 }</td>
		  	    <td>${course.courseNo }</td>
		  		<td>${course.courseName }</td>
		  	</tr>
		 </c:forEach>
	
	</table>
</div>




