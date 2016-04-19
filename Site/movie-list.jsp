<%@page import="com.sun.rowset.CachedRowSetImpl"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Movies</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="header.jsp" %>
	<div class="searchContainer">
		<input 
			id="dbSearchBox" 
			class="dbSearchBox" 
			placeholder="Movie Title, star name, year, or director " 
			onkeydown = "if (event.keyCode == 13)
                        document.getElementById('searchButton').click()" 
        	/>
		<input 
			id="searchButton" 
			class="searchButton" 
			type="button" 
			value="submit" 
			onclick="submitSearch()" 
			/>
	</div>
	<%CachedRowSetImpl rs = (CachedRowSetImpl)request.getSession().getAttribute("rs"); %>
	<%if(rs != null) {%>
		<%while(rs.next()){%>
			<p>
				<img src=${rs.getString(5)} alt="movieBanner" />
				${rs.getString(2)} ${rs.getString(3)} ${rs.getString(4)}
			</p>
		<% }%>
	<%} %>
	<script type="text/javascript" src="js/searchScript.js"></script>
</body>
</html>	