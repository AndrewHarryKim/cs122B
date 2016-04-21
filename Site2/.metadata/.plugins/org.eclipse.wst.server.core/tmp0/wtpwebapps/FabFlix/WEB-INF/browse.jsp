<%@ page import ="java.sql.*" %>
<link rel="stylesheet" type="text/css" href="css/styles.css">
<%@include file="header.jsp" %>
<%
	// This portion of the code is vital to make it so the user cannot access a page w/o being logged in.
	RequestDispatcher dispatcher = null;
    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
		String redirectURL = loginPath+"?message=";

		dispatcher = request.getServletContext().getRequestDispatcher(redirectURL);
		dispatcher.forward(request, response);
    }

    else if (request.getParameter("by") == null) 
    {
		String redirectURL = homePath;
		dispatcher = request.getServletContext().getRequestDispatcher(redirectURL);
		dispatcher.forward(request, response);
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

	    
	    out.println("<a href="+movieListPath+"?type=title&browseletter=a>a</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=b>b</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=c>c</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=d>d</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=e>e</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=f>f</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=g>g</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=h>h</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=i>i</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=j>j</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=k>k</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=l>l</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=m>m</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=n>n</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=o>o</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=p>p</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=q>q</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=r>r</a></br>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=s>s</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=t>t</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=u>u</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=v>v</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=w>w</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=x>x</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=y>y</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=z>z</a></br>");
	    /*
	    out.println("<a href="+movieListPath+"?type=title&browseletter=0>0</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=1>1</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=2>2</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=3>3</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=4>4</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=5>5</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=6>6</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=7>7</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=8>8</a>");
	    out.println("<a href="+movieListPath+"?type=title&browseletter=9>9</a>");*/
	    
	}
%>