<%@page import="com.sun.rowset.CachedRowSetImpl"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Movies</title>
<script type="text/javascript" src="js/searchScript.js"></script>
</head>
<body>
	<h2>Hello!  Please enter a keyword</h2>
	<input id="dbSearchBox" placeholder="Movie Title, star name, year, or director " />
	<input type="button" value="submit" onclick="submitSearch()" />
	<%CachedRowSetImpl rs = (CachedRowSetImpl)request.getSession().getAttribute("rs"); %>
	<%if(rs != null) {%>
		<%while(rs.next()){%>
			<p>
				<img src=${rs.getString(5)} alt="movieBanner" />
				${rs.getString(2)} ${rs.getString(3)} ${rs.getString(4)}
			</p>
		<% }%>
	<%} %>
</body>
</html>	
