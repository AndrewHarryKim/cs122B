
<%@include file="header.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // This portion of the code is vital to make it so the user cannot access a page w/o being logged in.
    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
        String redirectURL = loginPath+"?message=";

        RequestDispatcher dispatcher = null;
        dispatcher = getServletContext().getRequestDispatcher(redirectURL);
        dispatcher.forward(request, response);
    }
	
%>
<!DOCTYPE html>
<html>
    <head>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout</title>
    </head>
    <body>
        <form method="post" action=<%=ccAuthPath+""%>>
           <!-- credit number, expiration date, and first name and last name of the credit card holder. -->
           <!-- id:varchar(20), (primary key)
				first_name:varchar(50) 
				last_name:varchar(50) 
				expiration:date  -->
            <table border="1">
                <thead>
                    <tr>
                        <th colspan="2">Fabflix Checkout. Confirm the details.</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Credit Card Number</td>
                        <td><input type="text" name="cc_id" value="" /></td>
                    </tr>
                     <tr>
                        <td>First Name</td>
                        <td><input type="text" name="first_name" value="" /></td>
                    </tr>
                     <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="last_name" value="" /></td>
                    </tr>
                    <tr>
                        <td>Exp Date</td>
                        <td><input type="date" name="expiration" value="" /></td>
                    </tr>
                    
                    <tr>
                       
                        <td><input type="reset" value="Reset" /></td>
                         <td><input type="submit" value="Submit" /></td>
                    </tr>
                   
                    <!--
                    <tr>
                        <td colspan="2">Yet Not Registered!! <a href="reg.jsp">Register Here</a></td>
                    </tr>
                    -->
                </tbody>

            </table>
            <% 
                if (request.getParameter("confirmed") == null) {
                    //out.println("");
                } 
                else {
                    out.println("<div>"+"Invalid Credentials Entered."+"</div>");
                }
            %>
           
        </form>
    </body>
</html>