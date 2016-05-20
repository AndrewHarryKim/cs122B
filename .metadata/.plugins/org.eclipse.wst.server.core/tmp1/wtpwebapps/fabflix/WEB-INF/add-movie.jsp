<%@page import="fabflix.Global"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert Star</title>
</head>
<body class="employee-body">
<%@include file="dashboard-header.jsp" %>
	<form method="post" action=<%=request.getContextPath() + Global.addMovieServletPath %>>
            <table class="center loginTable" border="1">
                <thead>
                    <tr>
                        <th colspan="2">Add Movie</th>
                    </tr>
                </thead>
                <tbody>
                	<tr>
                        <td>Movie Title</td>
                        <td><input type="text" name="add-movie-title" value="" /></td>
                        <%if(request.getAttribute("noTitle") != null) {%>
	                        <%if((Boolean)request.getAttribute("noTitle")){ %><td>*Enter a Movie Title</td>
	                        <%} %>
                        <%} %>
                    </tr>
                	<tr>
                        <td>Director Name</td>
                        <td><input type="text" name="add-movie-director" value="" /></td>
                        <%if(request.getAttribute("noDirector") != null) {%>
	                        <%if((Boolean)request.getAttribute("noDirector")){ %><td>*Enter a Director Name</td>
	                        <%} %>
                        <%} %>
                    </tr>
                	<tr>
                        <td>Year of Release</td>
                        <td><input type="text" name="add-movie-year" value="" /></td>
                        <%if(request.getAttribute("noYear") != null) {%>
	                        <%if((Boolean)request.getAttribute("noYear")){ %><td>*Enter a year</td>
	                        <%} %>
                        <%} %>
                    </tr>
                    <tr>
                        <td>Star First-Name</td>
                        <td><input type="text" name="add-starfirstname" value="" /></td>
                        <%if(request.getAttribute("noStarFirst") != null) {%>
	                        <%if((Boolean)request.getAttribute("noStar")){ %><td>*Enter a Star First-Name</td>
	                        <%} %>
                        <%} %>
                    </tr>
                    <tr>
                        <td>Star Last-Name</td>
                        <td><input type="text" name="add-starlastname" value="" /></td>
                        <%if(request.getAttribute("noStarLast") != null) {%>
	                        <%if((Boolean)request.getAttribute("noStar")){ %><td>*Enter a Star Last-Name</td>
	                        <%} %>
                        <%} %>
                    </tr>
                    <tr>
                        <td>Genre Name</td>
                        <td><input type="text" name="add-genrename" value=""  /></td>
                        <%if(request.getAttribute("noGenre") != null) {%>
	                        <%if((Boolean)request.getAttribute("noGenre")){ %><td>*Enter a Genre Name</td>
	                        <%} %>
                        <%} %>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Submit" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                </tbody>

            </table>
            <% 
                if (request.getAttribute("add_movie_message") == null || ("".equals((String)request.getAttribute("add_movie_message")))) {
                    //out.println("");
                } 
                else {
                    out.println("<br/><br/><div class='center'>"+request.getAttribute("add_movie_message")+"</div>");
                }
            %>
            
        </form>
</body>
</html>