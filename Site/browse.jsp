<%@include file='var.jsp'%>
<%@ page import ="java.sql.*" %>
<%@ page import="web_tools.*" %>
<%
	// This portion of the code is vital to make it so the user cannot access a page w/o being logged in.
    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
		String redirectURL = loginPath+"?message=";
		response.sendRedirect(redirectURL);
    }

    else if (request.getParameter("by") == null) 
    {
		
		String redirectURL = browseGenre;
		response.sendRedirect(redirectURL);
    }
    else if (request.getParameter("by").equals("genre")) 
    {
		Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", DB_USER, DB_PASS);
	    // Statement st = con.createStatement();
	    // String query="select * from customers where email='" + email + "' and password= '" + pwd + "'";
	    // ResultSet rs = st.executeQuery(query);

	    String selectSQL = "select * from genres";
	    PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
	    // // preparedStatement.setInt(1, 1001);
	    ResultSet rs = preparedStatement.executeQuery(selectSQL );
	    // out.println(query);

	    while (rs.next()) 
	    {
	        
	       
	       out.println("<a href="+movieListPath+"?type=genre&id="+rs.getInt(1)+">"+rs.getString(2)+"</a></br>");
	    } 

    }      
	 else if (request.getParameter("by").equals("title")) 
    {
	
		Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", DB_USER, DB_PASS);
	    // Statement st = con.createStatement();
	    // String query="select * from customers where email='" + email + "' and password= '" + pwd + "'";
	    // ResultSet rs = st.executeQuery(query);

	    String selectSQL = "select * from genres";
	    PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
	    // // preparedStatement.setInt(1, 1001);
	    ResultSet rs = preparedStatement.executeQuery(selectSQL );
	    // out.println(query);

	    while (rs.next()) 
	    {
	        
	       
	       out.println("<a href="+movieListPath+"?type=genre&id="+rs.getString(1)+">"+rs.getString(2)+"</a></br>");
	    } 
	}
%>