<%@page import="jdbc.Contract"%>
<%@page import="java.util.Set"%>
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
Contract mContract = new Contract(10010);
mContract.setContent("fuck");
mContract.updateContract(mContract);
%>

</body>
</html>