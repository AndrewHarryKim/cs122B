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
<%
	// This portion of the code is vital to make it so the user cannot access a page w/o being logged in.
    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
		String redirectURL = loginPath+"?message=";

        RequestDispatcher dispatcher = null;
		dispatcher = getServletContext().getRequestDispatcher(redirectURL);
        dispatcher.forward(request, response);
    }

%>
	<div class="searchContainer">
		
	</div>
	<input 
			id="nameSearchBox" 
			class="dbSearchBox" 
			placeholder="Star Name" 
			onkeydown = "if (event.keyCode == 13)
                        document.getElementById('searchButton').click()" 
        	/>
        <input 
			id="titleSearchBox" 
			class="dbSearchBox" 
			placeholder="Movie Title" 
			onkeydown = "if (event.keyCode == 13)
                        document.getElementById('searchButton').click()" 
        	/>
        <input 
			id="directorSearchBox" 
			class="dbSearchBox" 
			placeholder="Director Full-Name" 
			onkeydown = "if (event.keyCode == 13)
                        document.getElementById('searchButton').click()" 
        	/>
        <input 
			id="yearSearchBox" 
			class="dbSearchBox" 
			placeholder="Year of Release" 
			onkeydown = "if (event.keyCode == 13)
                        document.getElementById('searchButton').click()" 
        	/>
		<input 
			id="searchButton" 
			class="searchButton" 
			type="button" 
			value="search" 
			onclick="submitSearch()" 
			/>
	<script type="text/javascript" src="js/search.js"></script>
</body>
</html>	