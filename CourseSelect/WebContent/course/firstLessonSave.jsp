<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function checkForm(){
		var firstLessonName=$("#firstLessonName").val();
		var firstLessonNo=$("#firstLessonNo").val();
		if(firstLessonName==null||firstLessonName==""){
			$("#error").html("课程名称不能为空！");
			return false;
		}
		if(firstLessonNo==null||firstLessonNo==""){
			$("#error").html("课程号不能为空！");
			return false;
		}
		return true;
	}
	
	function resetValue(){
		$("#firstLessonName").val("");
		$("#firstLessonNo").val("");
	}
</script>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">${actionName }</h3>
  </div>
  <div class="panel-body">
    	<form class="form-horizontal" role="form" method="post"  action="${pageContext.request.contextPath}/course?action=saveFirstLesson&courseNo=${courseNo}" onsubmit="return checkForm()" >
		  <div class="form-group">
		    <label  class="col-md-2 control-label">课程名称：</label>
		    <div class="col-md-10">
		      <input type="text" class="form-control" id="firstLessonName"  name="firstLessonName" style="width: 300px;"  value="">
		    </div>
		  </div>
		  <div class="form-group">
		    <label  class="col-md-2 control-label">课程号：</label>
		    <div class="col-md-10">
		      <input type="text" class="form-control" id="firstLessonNo"  name="firstLessonNo" style="width: 300px;"  value="">
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