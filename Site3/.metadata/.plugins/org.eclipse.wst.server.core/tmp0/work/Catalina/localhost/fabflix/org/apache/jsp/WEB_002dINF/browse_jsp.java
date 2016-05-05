/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.68
 * Generated at: 2016-05-02 18:22:44 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;
import fabflix.Global;

public final class browse_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/site-header.jsp", Long.valueOf(1461988399018L));
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
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("\t<title>fabflix - Browse Movies</title> \r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<header class=\"siteHeader\">\r\n");
      out.write("\t<nav class=\"navbar\">\r\n");
      out.write("\t\t<ul class=\"navlist\">\r\n");
      out.write("\t\t\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/Home\">Home</a></li>\r\n");
      out.write("\t\t\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/Search\">Search</a></li>\r\n");
      out.write("\t\t\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/Browse\">Browse</a></li>\r\n");
      out.write("\t\t\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/Cart\">View Cart</a></li>\r\n");
      out.write("\t\t</ul>\t\r\n");
      out.write("\t</nav>\r\n");
      out.write("</header>");
      out.write('\r');
      out.write('\n');

	// This portion of the code is vital to make it so the user cannot access a page w/o being logged in.
   if (request.getParameter("by") == null) 
    {
		String redirectURL = request.getContextPath() +  Global.homeServletPath;
		response.sendRedirect(redirectURL);
    }
    else if (request.getParameter("by").equals("genre")) 
    {
		Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", Global.DB_USER, Global.DB_PASS);
	   

	    String selectSQL = "select * from genres";
	    PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
	    ResultSet rs = preparedStatement.executeQuery(selectSQL );

	    while (rs.next()) 
	    {
	        
	       
	       out.println("<a href="+Global.movieListServletPath+"?type=genre&id="+rs.getInt(1)+">"+rs.getString(2)+"</a></br>");
	    } 

    }      
	 else if (request.getParameter("by").equals("title")) 
    {

	    
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=a>a</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=b>b</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=c>c</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=d>d</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=e>e</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=f>f</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=g>g</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=h>h</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=i>i</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=j>j</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=k>k</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=l>l</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=m>m</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=n>n</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=o>o</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=p>p</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=q>q</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=r>r</a></br>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=s>s</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=t>t</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=u>u</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=v>v</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=w>w</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=x>x</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=y>y</a>");
	    out.println("<a href="+Global.movieListServletPath+"?type=title&browseletter=z>z</a>");
	    /*
	    out.println("<br/><a href="+movieListPath+"?type=title&browseletter=0>0</a>");
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

      out.write("\r\n");
      out.write("</body>");
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