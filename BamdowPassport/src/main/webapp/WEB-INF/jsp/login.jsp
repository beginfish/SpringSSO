<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BamdowPassport</title>
</head>
<body>
	<p>welcome BamdowPassport</p>
	<form action="<%=request.getContextPath()%>/dologin.do" method="post">
		<table>
			 <c:if test="${error != ''}">
				<tr><td align="center" colspan="2">${error}</td ></tr>
			 </c:if>
			 <tr><td align="right">用户名：</td ><td align="left"><input type="text" name="username" value=""/></td></tr>
			 <tr><td align="right">密码：</td ><td align="left"><input type="password" name="password" value=""/></td></tr>
			 <tr><td align="center" colspan="2"><input type="submit" name="submit" value="登录"></td ></tr>
			 <input type="hidden" name="origin" value="${origin}"/>
		</table>
	</form>
</body>
</html>