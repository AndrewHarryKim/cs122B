<%@include file='var.jsp'%>
<%
session.setAttribute("id", null);
session.setAttribute("first_name", null);
session.setAttribute("last_name", null);
session.setAttribute("cc_id", null);
session.setAttribute("address", null);
session.setAttribute("email", null);
session.invalidate();

String redirectURL = homePath+"?message=Logged Out Sucessfully.";
response.sendRedirect(redirectURL);
// out.println("LOL");
%>