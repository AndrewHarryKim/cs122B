
/* A servlet to display the contents of the MySQL movieDB database */
/*
javac -d "/usr/local/Cellar/tomcat/8.0.33/libexec/webapps/TomcatForm/WEB-INF/classes" -classpath "/usr/local/Cellar/tomcat/8.0.33/libexec/webapps/TomcatForm/WEB-INF/sources/.:/usr/local/Cellar/tomcat/8.0.33/libexec/webapps/TomcatForm/WEB-INF/lib/servlet-api.jar:/usr/local/Cellar/tomcat/8.0.33/libexec/webapps/TomcatForm/WEB-INF/lib/mysql-connector-java-5.0.8-bin.jar" /usr/local/Cellar/tomcat/8.0.33/libexec/webapps/TomcatForm/WEB-INF/sources/LoginForm.java

*/

// package Fabflix.Login;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class TomcatForm extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and finds if a customer exists in the DB";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        HttpSession session = request.getSession();
        String loginUser = Global.DB_USER;
        String loginPasswd = Global.DB_USER;
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
        out.println("<BODY><H1>BESTMovieDB</H1>");


        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              String userid = request.getParameter("uname");    
              String pwd = request.getParameter("pass");
              Class.forName("com.mysql.jdbc.Driver");
              Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "root", "!FooBar1");
              Statement st = con.createStatement();
              // String query="select * from customers where email='" + userid + "' and password= '" + pwd + "'";
              // ResultSet rs = st.executeQuery(query);

              String selectSQL = "select * from customers where email='" + userid + "' and password='" + pwd + "'";
              PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
              // // preparedStatement.setInt(1, 1001);
              ResultSet rs = preparedStatement.executeQuery(selectSQL );
              // out.println(query);

              if (rs.next()) 
              {
                  //User s= new User();
                  session.setAttribute("userid", userid);
                  //out.println("welcome " + userid);
                  //out.println("<a href='logout.jsp'>Log out</a>");
                  response.sendRedirect("main.jsp");
              } 
              else 
              {
                  String redirectURL = "index.jsp?message=Invalid password or User Name. Try Again.";
                  response.sendRedirect(redirectURL);
              }
              rs.close();
              preparedStatement.close();
              dbcon.close();
            }
            catch (SQLException ex) {
                  while (ex != null) {
                        System.out.println ("SQL Exception:  " + ex.getMessage ());
                        ex = ex.getNextException ();
                    }  // end while
                }  // end catch SQLException

            catch(java.lang.Exception ex)
                {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
  doGet(request, response);
    }
}