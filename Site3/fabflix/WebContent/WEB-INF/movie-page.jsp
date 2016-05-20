
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

<%if(request.getParameter("addToCart")!= null && !request.getParameter("addToCart").equals("")){ %>
<body onload='updateQueryStringParameter("addToCart", "")'>
<%}else{ %>
<body>
<%} %>
<%ArrayList<String> genreNames = (ArrayList<String>)request.getAttribute("genreNames"); %>
<%ArrayList<Integer> genreIDs = (ArrayList<Integer>)request.getAttribute("genreIDs"); %>
<%ArrayList<String> starNames = (ArrayList<String>)request.getAttribute("starNames"); %>
<%ArrayList<Integer> starIDs = (ArrayList<Integer>)request.getAttribute("starIDs"); %>
<%@include file="site-header.jsp" %>
<%@include file="search-box.jsp" %>
	<div>
		<h2>${star}</h2>
		<div class="starInfoDiv"><img src="${banner_url}" alt="${title} Photo"/></div>
		<div>
			<div class="starInfoDiv"><div class="starInfoHeader">Movie ID#:</div> ${movie_id}
				 <input 
						id="addToCart" 
						class="addToCart" 
						type="button" 
						value="Add To Cart" 
						onclick='updateQueryStringParameter("addToCart", ${movie_id} )' />
			</div>
			<div class="starInfoDiv"><div class="starInfoHeader">Title:</div> ${title}</div>
			<div class="starInfoDiv"><div class="starInfoHeader">Year of Release:</div> ${year}</div>
			<div class="starInfoDiv"><div class="starInfoHeader">Director:</div> ${director}</div>
			<div class="starInfoHeader">Genres:</div> <br/>
			<div>
				<div class="indent">
					<%for(int i = 0;  i < genreNames.size(); i++) {%>
						<%request.setAttribute("tempString", (String)genreNames.get(i));%>
						<%request.setAttribute("tempInt", (int)genreIDs.get(i));%>
						<a href="/fabflix/MovieList?type=genre&id=${tempInt}">${tempString}</a>
						</div><div class="indent">
					<%} %>
				</div>
			</div>
			<div class="starInfoHeader">Stars:</div> <br/>
			<div>
				<div class="indent">
					<%for(int i = 0;  i < starNames.size(); i++) {%>
						<%request.setAttribute("tempString", (String)starNames.get(i));%>
						<%request.setAttribute("tempInt", (int)starIDs.get(i));%>
						<a href="/fabflix/StarPage?starname=${tempString}">${tempString}</a>
						</div><div class="indent">
					<%} %>
				</div>
			</div>
		</div>
		
	</div>
	
	<script type="text/javascript" src="js/movie-list.js"></script>
</body>
</html>

