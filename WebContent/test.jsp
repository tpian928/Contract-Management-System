<%@page import="java.util.ArrayList"%>
<%@page import="jdbc.Role"%>
<%@page import="obj.User"%>
<%@page import="jdbc.UserJDBCAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Role mRole = new Role("miye8eth2rarimu");
ArrayList<Integer> funcArr = new ArrayList<Integer>();
funcArr.add(1);

System.out.println(Role.hasThisFunc("10008", 29));
%>
</body>
</html>