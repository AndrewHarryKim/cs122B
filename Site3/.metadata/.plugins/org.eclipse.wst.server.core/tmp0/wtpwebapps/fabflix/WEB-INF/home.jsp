<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="fabflix.Global" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>fabflix - Home</title> 
	<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
	
	<%@include file="site-header.jsp" %>
	
	<span class="welcomeHeader">Welcome, <%=session.getAttribute("first_name")%> </span>
	<%
		out.println("</br>");
	    out.println("<a href='"+Global.browseGenreServlet+"'>Browse by Genre</a>");
	    out.println("<a href='"+Global.browseTitleServlet+"'>Browse by Title</a>");
	    out.println("<a href='"+Global.searchServletPath+"'>Advanced Search</a>");
	%>
</body>
</html>