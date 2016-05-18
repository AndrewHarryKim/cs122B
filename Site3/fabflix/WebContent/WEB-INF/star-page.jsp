
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<title>fabflix</title>
</head>
<body>
<%@include file="site-header.jsp" %>
<%@include file="search-box.jsp" %>

<%ArrayList<String> movieTitles = (ArrayList<String>)request.getAttribute("movieTitles"); %>
<%ArrayList<Integer> movieIDs = (ArrayList<Integer>)request.getAttribute("movieIDs"); %>
	<div>
		<h2>${star}</h2>
		<div class="starInfoDiv"><img src="${photo}" alt="${star} Photo"/></div>
		<div>
			<div class="starInfoDiv"><div class="starInfoHeader">Star ID#:</div> ${star_id}</div>
			<div class="starInfoDiv"><div class="starInfoHeader">Star Name:</div> ${star}</div>
			<div class="starInfoDiv"><div class="starInfoHeader">Date of Birth:</div> ${dob}</div>
			<div class="starInfoHeader">Movies:</div> <br/>
			<div>
				<div class="indent">
					<%for(int i = 0;  i < movieTitles.size(); i++) {%>
						<%request.setAttribute("tempString", (String)movieTitles.get(i));%>
						<%request.setAttribute("tempInt", (int)movieIDs.get(i));%>
						<a href="/fabflix/MoviePage?movieid=${tempInt}">${tempString}</a>
						</div><div class="indent">
					<%} %>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

