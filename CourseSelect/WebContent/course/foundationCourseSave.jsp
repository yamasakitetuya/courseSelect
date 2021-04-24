<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function checkForm(){
		var courseName=$("#courseName").val();
		var courseNo=$("#courseNo").val();
		if(courseName==null||courseName==""){
			$("#error").html("课程名称不能为空！");
			return false;
		}
		if(courseNo==null||courseNo==""){
			$("#error").html("课程号不能为空！");
			return false;
		}
		return true;
	}
	
	function resetValue(){
		$("#courseName").val("");
		$("#courseNo").val("");
	}
</script>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">${actionName }</h3>
  </div>
  <div class="panel-body">
    	<form class="form-horizontal" role="form" method="post"  action="${pageContext.request.contextPath}/course?action=saveFoundationCourse" onsubmit="return checkForm()" >
		  <div class="form-group">
		    <label  class="col-md-2 control-label">课程名称：</label>
		    <div class="col-md-10">
		      <input type="text" class="form-control" id="courseName"  name="courseName" style="width: 300px;"  value="${course.courseName }">
		    </div>
		  </div>
		  <div class="form-group">
		    <label  class="col-md-2 control-label">课程号：</label>
		    <div class="col-md-10">
		      <input type="text" class="form-control" id="courseNo"  name="courseNo" style="width: 300px;"  value="${course.courseNo}">
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <input type="hidden" id="id" name="id" value="${course.id }"/>
		      <button type="submit" class="btn btn-primary">保存</button>&nbsp;&nbsp;
		      <button type="button" class="btn btn-primary" onclick="resetValue()">重置</button>&nbsp;&nbsp;
		      <font color="red" id="error"></font>
		    </div>
		  </div>
		</form>
  </div>
</div>