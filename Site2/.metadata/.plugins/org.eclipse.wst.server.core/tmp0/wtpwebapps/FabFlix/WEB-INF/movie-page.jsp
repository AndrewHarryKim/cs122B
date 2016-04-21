<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<title>FabFlix</title>
</head>
<body>
<%ArrayList<String> genreNames = (ArrayList<String>)request.getAttribute("genreNames"); %>
<%ArrayList<Integer> genreIDs = (ArrayList<Integer>)request.getAttribute("genreIDs"); %>
<%ArrayList<String> starNames = (ArrayList<String>)request.getAttribute("starNames"); %>
<%ArrayList<Integer> starIDs = (ArrayList<Integer>)request.getAttribute("starIDs"); %>
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
	<div>
		<h2>${star}</h2>
		<div class="starInfoDiv"><img src="${banner_url}" alt="${title} Photo"/></div>
		<div>
			<div class="starInfoDiv"><div class="starInfoHeader">Movie ID#:</div> ${movie_id}</div>
			<div class="starInfoDiv"><div class="starInfoHeader">Title:</div> ${title}</div>
			<div class="starInfoDiv"><div class="starInfoHeader">Year of Release:</div> ${year}</div>
			<div class="starInfoDiv"><div class="starInfoHeader">Director:</div> ${director}</div>
			<div class="starInfoHeader">Genres:</div> <br/>
			<div>
				<div class="indent">
					<%for(int i = 0;  i < genreNames.size(); i++) {%>
						<%request.setAttribute("tempString", (String)genreNames.get(i));%>
						<%request.setAttribute("tempInt", (int)genreIDs.get(i));%>
						<a href="/FabFlix/MovieList?type=genre&id=${tempInt}">${tempString}</a>
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
						<a href="/FabFlix/StarPage?starname=${tempString}">${tempString}</a>
						</div><div class="indent">
					<%} %>
				</div>
			</div>
		</div>
		
	</div>
</body>
</html>

