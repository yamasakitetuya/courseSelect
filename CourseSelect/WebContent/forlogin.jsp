<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

			<div align="center">
				<p>请先登录</p>
				<p><span id="timeCount">3</span>秒后跳转到登录页面...</p>
				<!-- <script type="text/javascript">
					setTimeout("location.href='login.jsp'", 3000);
				</script>  -->
			</div>
</body>
<script type="text/javascript">
	onload = function(){
		setInterval(go,1000);
	}
	var time = 3;
	function go(){
		time--;
		if (time > 0){
			document.getElementById("timeCount").innerHTML = time;
		}else{
			location.href="login.jsp"; 
		}
		
	}
</script>
</html>