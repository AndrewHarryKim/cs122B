<%@page import="fabflix.CartList"%>
<%@page import="fabflix.CartItem"%>
<%@page import="fabflix.Global"%>


<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Your Cart</title>
</head>
<body>
<%
	if (session.getAttribute("cart") == null) {
		out.println("NOTHING FOUND IN CART");
	} 
	else {

		CartList cart = (CartList) (session.getAttribute("cart"));
		if (request.getParameter("del") != null) {
			cart.removeByID(Integer.parseInt(request.getParameter("del")));
			
		}
	}
	int title = 0;
%>
<%@include file="site-header.jsp"%>
	<form id="checkoutForm" action=<%=Global.checkoutServletPath%> method="post"></form>
	<form id="deleteForm" method="post"></form>
	<div id="toolbar">

		<table border="1" cellpadding="3">
			<thead>
				<tr>
					<th colspan="100%">Your Cart</th>
				</tr>
			</thead>
			<tbody>
				<%
					if (session.getAttribute("cart") == null) {
						out.println("<tr>");
						out.println("	<td>Nothing Found</td>");
						out.println("</tr>");
					} else {

						int count = 0;
						CartList cart = (CartList) (session.getAttribute("cart"));
						out.println("<tr>");
						out.println("	<td>Title</td>");
						out.println("	<td>Year</td>");
						out.println("	<td>Director</td>");
						out.println("	<td></td>");

						out.println("	<td></td>");
						out.println("</tr>");
						for (CartItem C : cart.cart) {
							out.println("<tr>");
							out.println("	<td>" + C.title + "</td>");
							out.println("	<td>" + C.year + "</td>");
							out.println("	<td>" + C.director + "</td>");
							out.println(
									"	<td><img style='width:20%;' src='" + C.banner_url + "' alt='" + C.title + "' </td>");
							%>
							<td><input type=button onClick="location.href='<%=Global.cartServletPath%>?del=<%=C.movieId%>'" value='delete'></td>
							<%							
							out.println("</tr>");
						}
					}
				%>
			</tbody>
		</table>
		
		<input type="submit" form="checkoutForm" value="Checkout" />
		<%
			if (request.getParameter("message") != null) {
				out.println("<div>" + request.getParameter("message") + "</div>");
			}
			
		%>

	</div>
</body>
</html>

