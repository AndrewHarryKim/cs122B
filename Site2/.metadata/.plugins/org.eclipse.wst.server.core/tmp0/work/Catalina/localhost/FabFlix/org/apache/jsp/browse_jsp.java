/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.68
 * Generated at: 2016-04-20 06:45:23 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;

public final class browse_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/var.jsp", Long.valueOf(1461134538654L));
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!-- This File holds the Login Details and other Globals -->\r\n");

	// This is the Login for the DB
	String DB_USER= "root"; 
	String DB_PASS="!FooBar1";
	// This is the location of the Login Portal
	String loginPath="index.jsp";
	// This is the location of the Login-Authentication
	String authLoginPath="login.jsp";	
	// This is the location of the Logout script
	String logoutPath="logout.jsp";	
	//This is the location of the HomePage
	String homePath="main.jsp";
	// This is the location of the Search Page
	String searchPath="";	
	// This is the location of the General Browsing Page and the sub-pages
	String browsePath="browse.jsp";
	String browseGenre="browse.jsp?by=genre";
	String browseTitle="browse.jsp?by=title";
	// This is the location of the movie-list after you find one.
	String movieListPath="movie-list.jsp";

      out.write("\r\n");
      out.write("\r\n");

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

    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}