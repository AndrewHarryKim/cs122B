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
	<form method="post" action=<%=request.getContextPath() + Global.insertStarServletPath %>>
            <table class="center loginTable" border="1">
                <thead>
                    <tr>
                        <th colspan="2">Insert Star</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Star Name*</td>
                        <td><input type="text" name="insert-star-name" value="" /></td>
                    </tr>
                    <tr>
                        <td>Date of Birth</td>
                        <td><input type="text" name="insert-star-dob" value="" placeholder="DD/MM/YYYY" /></td>
                    </tr>
                    <tr>
                        <td>Photo URL</td>
                        <td><input type="text" name="insert-star-photo-url" value=""  /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Submit" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                   
                </tbody>

            </table>
            <div class='center'>*required</div>
            <% 
                if (request.getAttribute("insert_star_message") == null || ("".equals((String)request.getAttribute("insert_star_message")))) {
                    //out.println("");
                } 
                else {
                    out.println("<br/><br/><div class='center'>"+request.getAttribute("insert_star_message")+"</div>");
                }
            %>
            
        </form>
</body>
</html>