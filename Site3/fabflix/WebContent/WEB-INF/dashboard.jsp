

<%@page import="fabflix.Global" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
</head>
<body class="employee-body">
<%@include file="dashboard-header.jsp" %>
	<a href=<%=request.getContextPath() +Global.insertStarServletPath%>><input type=button value="Insert Star"/></a>
	<a href=<%=request.getContextPath() +Global.metaDataServletPath%>><input type=button value="Get MetaData"/></a>
	<a href=<%=request.getContextPath() +Global.addMovieServletPath%>><input type=button value="Add Movie"/></a>
</body>
</html>