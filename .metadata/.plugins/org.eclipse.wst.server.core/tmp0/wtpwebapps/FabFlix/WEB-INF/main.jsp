
<%@include file="header.jsp" %>
<link rel="stylesheet" type="text/css" href="css/styles.css">
<%
	// This portion of the code is vital to make it so the user cannot access a page w/o being logged in.
    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
		String redirectURL = loginPath+"?message=";

        RequestDispatcher dispatcher = null;
		dispatcher = getServletContext().getRequestDispatcher(redirectURL);
        dispatcher.forward(request, response);
    }

%>
Welcome to Fabflix, <%=session.getAttribute("first_name")%>
</br><a align= "right" href=<%=logoutPath+""%>>Log out</a>
<%
	out.println("</br>");
    out.println("<a href='"+browseGenre+"'>Browse by Genre</a>");
    out.println("<a href='"+browseTitle+"'>Browse by Title</a>");
    out.println("<a href='search'>Advanced Search</a>");
%>