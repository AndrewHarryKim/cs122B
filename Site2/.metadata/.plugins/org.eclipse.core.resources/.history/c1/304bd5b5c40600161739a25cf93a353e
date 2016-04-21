<%@include file='var.jsp'%>
<%
	// This portion of the code is vital to make it so the user cannot access a page w/o being logged in.
    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
		String redirectURL = loginPath+"?message=";
		response.sendRedirect(redirectURL);
    }

%>

Welcome <%=session.getAttribute("first_name")%>
<a align= "right" href=<%=logoutPath+""%>>Log out</a>
<%
	out.println("</br>");
    out.println("<a href='"+browseGenre+"'>Browse by Genre</a>");
    out.println("<a href='"+browseTitle+"'>Browse by Title</a>");
%>