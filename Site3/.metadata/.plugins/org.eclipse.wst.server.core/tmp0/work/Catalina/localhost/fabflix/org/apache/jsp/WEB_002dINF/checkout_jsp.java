/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.68
 * Generated at: 2016-05-02 18:44:35 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import fabflix.Global;

public final class checkout_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
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
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("    <head>\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\">\r\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("        <title>Checkout</title>\r\n");
      out.write("    </head>\r\n");
      out.write("    <body>\r\n");
      out.write("    \r\n");
      out.write("\t\t");
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
      out.write("\r\n");
      out.write("        <form method=\"post\" action=");
      out.print(Global.checkCCServletPath);
      out.write(">\r\n");
      out.write("           <!-- credit number, expiration date, and first name and last name of the credit card holder. -->\r\n");
      out.write("           <!-- id:varchar(20), (primary key)\r\n");
      out.write("\t\t\t\tfirst_name:varchar(50) \r\n");
      out.write("\t\t\t\tlast_name:varchar(50) \r\n");
      out.write("\t\t\t\texpiration:date  -->\r\n");
      out.write("            <table border=\"1\">\r\n");
      out.write("                <thead>\r\n");
      out.write("                    <tr>\r\n");
      out.write("                        <th colspan=\"2\">Fabflix Checkout. Confirm the details.</th>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                </thead>\r\n");
      out.write("                <tbody>\r\n");
      out.write("                    <tr>\r\n");
      out.write("                        <td>Credit Card Number</td>\r\n");
      out.write("                        <td><input type=\"text\" name=\"cc_id\" value=\"\" /></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                     <tr>\r\n");
      out.write("                        <td>First Name</td>\r\n");
      out.write("                        <td><input type=\"text\" name=\"first_name\" value=\"\" /></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                     <tr>\r\n");
      out.write("                        <td>Last Name</td>\r\n");
      out.write("                        <td><input type=\"text\" name=\"last_name\" value=\"\" /></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                    <tr>\r\n");
      out.write("                        <td>Exp Date</td>\r\n");
      out.write("                        <td><input type=\"date\" name=\"expiration\" value=\"\" /></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                    \r\n");
      out.write("                    <tr>\r\n");
      out.write("                       \r\n");
      out.write("                        <td><input type=\"reset\" value=\"Reset\" /></td>\r\n");
      out.write("                         <td><input type=\"submit\" value=\"Submit\" /></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                   \r\n");
      out.write("                </tbody>\r\n");
      out.write("\r\n");
      out.write("            </table>\r\n");
      out.write("            ");
 
                if (request.getParameter("confirmed") == null) {
                    //out.println("");
                } 
                else {
                    out.println("<div>"+"Invalid Credentials Entered."+"</div>");
                }
            
      out.write("\r\n");
      out.write("           \r\n");
      out.write("        </form>\r\n");
      out.write("    </body>\r\n");
      out.write("</html>");
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
