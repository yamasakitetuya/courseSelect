<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
 	var currentPage=1;
	var pageSize = 3;
	var count = ${selectCourseList.size()};
	var totalPage = parseInt(count/pageSize) + ((count % pageSize) == 0? 0 : 1);
	function checkCurrentPage(){
		if(currentPage==1){
			document.getElementById("toLast").setAttribute("class","disabled");
			document.getElementById("codeInsert").removeAttribute("class");
			document.getElementById("lastPage").removeAttribute("onclick");
			document.getElementById("nextPage").setAttribute("onclick","toNextPage()");	
		}else if (currentPage==totalPage){
			document.getElementById("codeInsert").setAttribute("class","disabled");
			document.getElementById("toLast").removeAttribute("class");
			document.getElementById("nextPage").removeAttribute("onclick");
			document.getElementById("lastPage").setAttribute("onclick","toLastPage()");	
		}else if (currentPage >1 && currentPage<totalPage){
			document.getElementById("codeInsert").removeAttribute("class");
			document.getElementById("toLast").removeAttribute("class");
			document.getElementById("lastPage").setAttribute("onclick","toLastPage()");
			document.getElementById("nextPage").setAttribute("onclick","toNextPage()");	
		}
		
	}
	function toFirstPage(){
		$("tr.showSelectCourse").each(function(){
			if(parseInt($(this).attr("id"))>=1 && parseInt($(this).attr("id"))<=pageSize){
				$(this).attr("style","");
			}else{
				$(this).attr("style","display: none")
			}
		});
		currentPage = 1;
		checkCurrentPage();
		
		
	}
	
	function toEndPage(){
		$("tr.showSelectCourse").each(function(){
			if(parseInt($(this).attr("id"))>=(totalPage-1)*pageSize+1 && parseInt($(this).attr("id"))<=totalPage*pageSize){
				$(this).attr("style","");
			}else{
				$(this).attr("style","display: none")
			}
		});	
		currentPage = totalPage;
		checkCurrentPage();
	}
	
	function toSelectedPage(){
		var e = window.event;
        obj = e.target;
		pageCode = parseInt(obj.innerText);
		$("tr.showSelectCourse").each(function(){
			if(parseInt($(this).attr("id"))>=(pageCode-1)*pageSize+1 && parseInt($(this).attr("id"))<=pageCode*pageSize){
				$(this).attr("style","");
			}else{
				$(this).attr("style","display: none")
			}
		});	
		currentPage = pageCode;
		checkCurrentPage();
		
	}
	
	function toLastPage(){
		pageCode = --currentPage;
		$("tr.showSelectCourse").each(function(){
			if(parseInt($(this).attr("id"))>=(pageCode-1)*pageSize+1 && parseInt($(this).attr("id"))<=pageCode*pageSize){
				$(this).attr("style","");
			}else{
				$(this).attr("style","display: none")
			}
		});
		checkCurrentPage();
		
	}
	
	function toNextPage(){
		pageCode = ++currentPage;
		$("tr.showSelectCourse").each(function(){
			if(parseInt($(this).attr("id"))>=(pageCode-1)*pageSize+1 && parseInt($(this).attr("id"))<=pageCode*pageSize){
				$(this).attr("style","");
			}else{
				$(this).attr("style","display: none")
			}
		});
		checkCurrentPage();
		
	}
	
	
	
	function generatePageCode(){
		for(i=currentPage;i<=currentPage+5;i++){
			if(i<=totalPage){
				var li = document.createElement("li");
				var a = document.createElement("a");
				var liStart = document.getElementById("codeInsert");
				var txt = document.createTextNode(i);
				a.setAttribute("class","codeControl");
				a.setAttribute("onclick","toSelectedPage()");
				a.appendChild(txt);
				li.append(a);
				liStart.parentNode.insertBefore(li,liStart);
			}
		}
		toFirstPage();
	}
		
		
	// 选择课程
	function selectCourse(){
		var chk_value=[];
		$('input[name="s_courseIds"]:checked').each(function(){
			chk_value.push($(this).val());
		});
		if(chk_value.length==0){
			alert("请选择要选的课程");
			return;
		}
		var courseIds=chk_value.join(",");
		if(confirm("您确认要选择这些课程吗？")){
			$.post("student?action=selectCourse",{courseIds:courseIds},
				function(result){
					var result=eval('('+result+')');
					if(result.success){
						alert("执行成功");
						window.location.href="${pageContext.request.contextPath}/student?action=preSelect";
					}else{
						alert(result.errorMsg);
					}
				}
			);
		}
	}
	
	// 退选课程
	function unselectCourse(){
		var chk_value=[];
		$('input[name="scIds"]:checked').each(function(){
			chk_value.push($(this).val());
		});
		if(chk_value.length==0){
			alert("请选择要退选的课程");
			return;
		}
		var scIds=chk_value.join(",");
		if(confirm("您确认要退选这些课程吗？")){
			$.post("student?action=unSelectCourse",{scIds:scIds},
				function(result){
					var result=eval('('+result+')');
					if(result.success){
						alert("执行成功");
						window.location.href="${pageContext.request.contextPath}/student?action=preSelect";
					}else{
						alert("课程已经打分，不能退选");
					}
				}
			);
		}
	}
	//产生分页器
	$(document).ready(function(){
		generatePageCode();
			
	})
	
	

</script>

<div class="row search">
	<form action="${pageContext.request.contextPath}/student?action=preSelect" accept-charset="utf-8" method="post">
		<div class="col col-md-4">
			<div class="input-group" style="width: 300px">
				<input type="text" class="form-control" name="s_courseName"  placeholder="请输入要查询的课程名称..."> 
			</div>
		</div> 
		<div class="col col-md-4">
			<div class="input-group" style="width: 300px">
				<input type="text" class="form-control" name="s_profession"  placeholder="请输入要查询的课程的专业码...">
			</div>
		</div>  
		<div class ="col col-md-4">
			<span class="input-group-btn">
				<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button>
			</span> 
		</div>
	</form>	
</div>


<div class="panel panel-default">
  <div class="panel-heading">可选课程</div>
      <table class="table table-hover  table-bordered table-striped">
        <thead>
        	<tr>
        	    <th>&nbsp;</th>
        		<th>序号</th>
        		<th>课号</th>
			  	<th>课程名称</th>
			  	<th>课程时间</th>
			  	<th>上课地点</th>
			  	<th>学分</th>
			  	<th>授课老师</th>
        	</tr>
          <c:forEach var="selectCourse" items="${selectCourseList }" varStatus="status">
		  	<tr id="${status.index+1 }" class="showSelectCourse">
		  		<td><input type="checkbox" name="s_courseIds" value="${selectCourse.id}"/></td>
		  	    <td>${status.index+1 }</td>
		  	    <td>${selectCourse.courseNo }</td>
		  		<td>${selectCourse.courseName }</td>
		  		<td>${selectCourse.courseTime }</td>
		  		<td>${selectCourse.classroom }</td>
		  		<td>${selectCourse.credit }</td>
		  		<td>${selectCourse.teacherName }</td>
		  	</tr>
		  </c:forEach>
        </tbody>
      </table>
	<button type="button" class="btn btn-success" style="margin: 5px" onclick="selectCourse()">选择课程</button>
</div>
<nav>
	<ul class="pagination">
		<li><a id="pageStart" onclick="toFirstPage()">首页</a></li>
		<li id="toLast"><a id="lastPage" onclick="toLastPage()">上一页</a></li>
		<li id="codeInsert"><a id="nextPage" onclick="toNextPage()">下一页</a></li>
		<li><a id="endPage" onclick="toEndPage()">尾页</a><li>
	</ul>
</nav>
<hr/>
<div class="panel panel-default">
  <div class="panel-heading">已选课程</div>
      <table class="table table-hover  table-bordered table-striped">
        <thead>
        	<tr>
        	    <th>&nbsp;</th>
        		<th>序号</th>
			  	<th>课程名称</th>
			  	<th>课程时间</th>
			  	<th>上课地点</th>
			  	<th>学分</th>
			  	<th>授课老师</th>
        	</tr>
          <c:forEach var="course" items="${myCourseList }" varStatus="status">
		  	<tr>
		  		<td><input type="checkbox" name="scIds" value="${course.id }"/></td>
		  	    <td>${status.index+1 }</td>
		  		<td>${course.courseName }</td>
		  		<td>${course.courseTime }</td>
		  		<td>${course.classroom }</td>
		  		<td>${course.credit }</td>
		  		<td>${course.teacherName }</td>
		  	</tr>
		  </c:forEach>
        </tbody>
      </table>
      <button type="button" class="btn btn-danger" style="margin: 5px" onclick="unselectCourse()">退选课程</button>
</div>
<hr/>
<div class="panel panel-default">
  <div class="panel-heading">可选择的专业必修课程</div>
      <table class="table table-hover  table-bordered table-striped">
        <thead>
        	<tr>
        	    <th>&nbsp;</th>
        		<th>序号</th>
        		<th>课号</th>
			  	<th>课程名称</th>
			  	<th>课程时间</th>
			  	<th>上课地点</th>
			  	<th>学分</th>
			  	<th>授课老师</th>
        	</tr>
          <c:forEach var="course" items="${professionCourseListNow }" varStatus="status">
		  	<tr>
		  		<td><input type="checkbox" name="s_courseIds" value="${course.id }"/></td>
		  	    <td>${status.index+1 }</td>
		  	    <td>${course.courseNo }</td>
		  		<td>${course.courseName }</td>
		  		<td>${course.courseTime }</td>
		  		<td>${course.classroom }</td>
		  		<td>${course.credit }</td>
		  		<td>${course.teacherName }</td>
		  	</tr>
		  </c:forEach>
        </tbody>
      </table>
      <button type="button" class="btn btn-success" style="margin: 5px" onclick="selectCourse()">选择课程</button>
</div>





