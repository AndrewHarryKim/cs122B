<%@page import="java.util.ArrayList"%>
<%@page import="com.sun.rowset.CachedRowSetImpl"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="fabflix.Global"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<title>Search Movies</title>
</head>
<%if(request.getParameter("addToCart") != null && !request.getParameter("addToCart").equals("")){ %>
<body onload='updateQueryStringParameter("addToCart", "")'>
<%}else{ %>
<body>
<%} %>
<%@include file="site-header.jsp" %>
<%CachedRowSetImpl rs = (CachedRowSetImpl)request.getAttribute("rs"); %>
<%ArrayList<ArrayList<String>> moviesAndStars = (ArrayList<ArrayList<String>>)request.getAttribute("moviesAndStars"); %>
<%ArrayList<ArrayList<String>> moviesAndGenres = (ArrayList<ArrayList<String>>)request.getAttribute("moviesAndGenres"); %>
<%ArrayList<ArrayList<Integer>> moviesAndGenreIDs = (ArrayList<ArrayList<Integer>>)request.getAttribute("moviesAndGenreIDs"); %>
<%Integer pageNum = (Integer)request.getAttribute("pageNum"); %>
<%if(rs != null) {%>
<%int j = 0; %>
<%String tempString; %>
<%Integer tempInt; %>
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
	
		<div class="movieListHeader">
			<div>Movie ID</div> 
			<div class="movieName" onclick='updateQueryStringParameter("orderby", "title")'>Movie Title</div> 
			<div class="movieYear" onclick='updateQueryStringParameter("orderby", "year")'>Year of Release</div>  
			<div>Director</div>
			<div class="movieStarList">Star Names</div>
			<div class="movieGenreList">Genres</div>
		</div>
		<%while(rs.next()){%>
			<div class="movieRow">
				<div class="movieInfoDiv"> 
					<%=rs.getInt(1)%>
					<input 
						id="addToCart" 
						class="addToCart" 
						type="button" 
						value="Add To Cart" 
						onclick='updateQueryStringParameter("addToCart",<%=rs.getInt(1)%> )' />
				</div> 
				<div class="movieInfoDiv movieName"> <a href="/fabflix/MoviePage?movieid=<%=rs.getInt(1)%>"><%=rs.getString(2)%></a> </div> 
				<div class="movieInfoDiv"> <%=rs.getString(3)%> </div>  
				<div class="movieInfoDiv"> <%=rs.getString(4)%> </div>
				<div class="movieStarList movieInfoDiv">
					<div>
					<%for(int i = 0;  i < moviesAndStars.get(j).size(); i++) {%>
						<%tempString = (String)moviesAndStars.get(j).get(i);%>
						<a href="/fabflix/StarPage?starname=<%=tempString%>"><%=tempString%></a>
						<%if((i+1)%3 == 0){ %>
						</div><div>
						<%} %>
					<%} %>
					</div>
				</div>
				<div class="movieGenreList movieInfoDiv">
					<div>
					<%for(int i = 0;  i < moviesAndGenres.get(j).size(); i++) {%>
						<%tempString = (String)moviesAndGenres.get(j).get(i);%>
						<%tempInt = (Integer)moviesAndGenreIDs.get(j).get(i);%>
						<a href="MovieList?type=genre&id=<%=tempInt%>"><%=tempString%></a>
						<%if((i+1)%4 == 0){ %>
						</div><div>
						<%} %>
					<%} %>
					</div>
				</div>
			</div>
			<%j++; %>
		<%} %>
		<%if(pageNum != null) {%>
			<%if(!pageNum.equals("")) {%>
				<%if(pageNum > 1) {%>
					<input 
						id="prevButton" 
						class="prevButton" 
						type="button" 
						value="prev" 
						onclick='decrementQueryStringParameter("pagenum", <%=pageNum%>)' 
						/>
				<%}%>
			<%} %>
		<%} else {%>
			<input 
				id="prevButton" 
				class="prevButton" 
				type="button" 
				value="prev" 
				onclick='decrementQueryStringParameter("pagenum", 2)' 
				/>
		<%} %>
		<%if( !(j < 10) ){ %>
			<%if(pageNum != null) {%>
				<%if(!pageNum.equals("")) {%>
					<input 
						id="nextButton" 
						class="nextButton" 
						type="button" 
						value="next" 
						onclick='incrementQueryStringParameter("pagenum", <%=pageNum%>)' 
						/>
				<%} %>
			<%} else {%>
				<input 
					id="nextButton" 
					class="nextButton" 
					type="button" 
					value="next" 
					onclick='incrementQueryStringParameter("pagenum", 1)' 
					/>
			<%} %>
		<%} %>
	<%}else{ %>
	<p>No movies match your search</p>
	<%} %>
	<script type="text/javascript" src="js/movie-list.js"></script>
</body>
</html>	