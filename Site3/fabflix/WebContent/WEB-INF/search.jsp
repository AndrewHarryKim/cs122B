
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>fabflix - Search Movies</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>

<%@include file="site-header.jsp" %>
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