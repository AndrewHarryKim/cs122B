
<%@include file="header.jsp" %>
<%@page import="com.simple.CartList" %>
<%@page import="com.simple.CartItem" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
<%
    // This portion of the code is vital to make it so the user cannot access a page w/o being logged in.
    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
        String redirectURL = loginPath+"?message=";

        RequestDispatcher dispatcher = null;
        dispatcher = getServletContext().getRequestDispatcher(redirectURL);
        dispatcher.forward(request, response);
    }
	
%>
<%
	if(session.getAttribute("cart")==null)
	{
		out.println("NOTHING FOUND IN CART");
	}
	else
	{

		
		CartList cart=(CartList)(session.getAttribute("cart"));
		/* out.println("\nCART FOUND"); */
	}
	int title=0;
 %>
<!DOCTYPE html>
<html>
    <head>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart</title>
    </head>
    <body>
    <form id="checkoutForm" action=<%=checkoutPath+""%> method="post"></form>
	<form id="deleteForm" method="post"></form>
        <div id="toolbar">
            
            <table border="1"  cellpadding="3">
                <thead>
                    <tr>
                        <th colspan="100%">Your Cart</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- <tr>
                        <td>Email/ User Name</td>
                        <td><input type="text" name="email" value="" /></td>
                    </tr> -->
<%
	if(session.getAttribute("cart")==null)
	{
		out.println("<tr>");
		out.println("	<td>Nothing Found</td>");
		out.println("</tr>");
	}
	else
	{

		int count=0;
		CartList cart=(CartList)(session.getAttribute("cart"));
		out.println("<tr>");
		out.println("	<td>Title</td>");
		out.println("	<td>Year</td>");
		out.println("	<td>Director</td>");
		out.println("	<td></td>");
		
		out.println("	<td></td>");
		out.println("</tr>");
		for(CartItem C:cart.cart)
		{
			out.println("<tr>");
			out.println("	<td>"+C.title+"</td>");
			out.println("	<td>"+C.year+"</td>");
			out.println("	<td>"+C.director+"</td>");
			out.println("	<td><img style='width:20%;' src='"+C.banner_url+"' alt='"+C.title+"' </td>");
			String a="";
			out.println("	<td>"+ a+"</td>");
			
			
			
			out.println("</tr>");
		}
		/* out.println("\nCART FOUND"); */
	}
 %>
                    <!-- <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" value="" /></td>
                     -->
                  
                   
                    <!--
                    <tr>
                        <td colspan="2">Yet Not Registered!! <a href="reg.jsp">Register Here</a></td>
                    </tr>
                    -->
                </tbody>

            </table>
             
                        <input type="submit" form= "checkoutForm" value="Checkout" />
                   <!--      <td><input type="reset" value="Reset" /></td> -->
                    
            <% 
                if (request.getParameter("message") == null) {
                    //out.println("");
                } 
                else {
                    out.println("<div>"+request.getParameter("message")+"</div>");
                }
            %>
        
        </div>
    </body>
</html>
