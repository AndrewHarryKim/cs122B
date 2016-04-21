<%@page import="java.util.ArrayList"%>
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
			value="search" 
			onclick="submitSearch()" 
			/>
	</div>
	<%CachedRowSetImpl rs = (CachedRowSetImpl)request.getSession().getAttribute("rs"); %>
	<%ArrayList<ArrayList<String>> moviesAndStars = (ArrayList<ArrayList<String>>)request.getSession().getAttribute("moviesAndStars"); %>
	<%ArrayList<ArrayList<String>> moviesAndGenres = (ArrayList<ArrayList<String>>)request.getSession().getAttribute("moviesAndGenres"); %>
	<%ArrayList<ArrayList<Integer>> moviesAndGenreIDs = (ArrayList<ArrayList<Integer>>)request.getSession().getAttribute("moviesAndGenreIDs"); %>
	<%if(rs != null) {%>
	<%int j = 0; %>
		<div class="movieListHeader">
			<div>Movie ID</div> 
			<div class="movieName">Movie Title</div> 
			<div>Year of Release</div>  
			<div>Director</div>
			<div class="movieStarList">Star Names</div>
			<div class="movieGenreList">Genres</div>
		</div>
		<%while(rs.next()){%>
			<div class="movieRow">
				<div class="movieInfoDiv"> ${rs.getInt(1)} </div> 
				<div class="movieInfoDiv movieName"> <a href="/FabFlix/MoviePage?movieid=${rs.getInt(1)}">${rs.getString(2)}</a> </div> 
				<div class="movieInfoDiv"> ${rs.getString(3)} </div>  
				<div class="movieInfoDiv"> ${rs.getString(4)} </div>
				<div class="movieStarList movieInfoDiv">
					<div>
					<%for(int i = 0;  i < moviesAndStars.get(j).size(); i++) {%>
						<%request.getSession().setAttribute("tempString", (String)moviesAndStars.get(j).get(i));%>
						<a href="/FabFlix/StarPage?starname=${tempString}">${tempString}</a>
						<%if((i+1)%3 == 0){ %>
						</div><div>
						<%} %>
					<%} %>
					</div>
				</div>
				<div class="movieGenreList movieInfoDiv">
					<div>
					<%for(int i = 0;  i < moviesAndGenres.get(j).size(); i++) {%>
						<%request.getSession().setAttribute("tempString", (String)moviesAndGenres.get(j).get(i));%>
						<%request.getSession().setAttribute("tempInt", (Integer)moviesAndGenreIDs.get(j).get(i));%>
						<a href="MovieList?type=genre&id=${tempInt}">${tempString}</a>
						<%if((i+1)%4 == 0){ %>
						</div><div>
						<%} %>
					<%} %>
					</div>
				</div>
			</div>
			<%j++; %>
		<%} %>
		
	<%}else{ %>
	<p>No movies match your search</p>
	<%} %>
	<script type="text/javascript" src="js/movie-list.js"></script>
</body>
</html>	