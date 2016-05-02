<%@page import="fabflix.Global"%>
<header class="siteHeader">
	<nav class="navbar">
		<ul class="navlist">
			<li><a href="<%=request.getContextPath()%>/Home">Home</a></li>
			<li><a href="<%=request.getContextPath()%>/Search">Search</a></li>
			<li><a href="<%=request.getContextPath()%>/Browse">Browse</a></li>
			<li><a href="<%=request.getContextPath()%>/Cart">View Cart</a></li>
			<% if(!(session.getAttribute("email") == null) || (session.getAttribute("email") == "")){%>
				<a href=<%=Global.logoutServletPath+""%>><input class="absolute-right" type="button" value="logout"/></a>
			<%} %>
		</ul>	
	</nav>
</header>