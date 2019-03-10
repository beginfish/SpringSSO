<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BamdowTwo</title>
</head>
<body>
	<p>first two</p>
	<h>this show must be login</h>
	<a href="#" onclick="logout()">登出</a>
<script>
function logout(){
	location.href="http://localhost:8081/bamdowPassport/loginout.do?originurl="+encodeURI("http://localhost:8082/bamdowTwo");
}
</script>
</body>
</html>