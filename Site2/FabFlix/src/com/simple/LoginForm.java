package com.simple;

import com.simple.Global;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class LoginForm
 */
@WebServlet("/LoginForm")
public class LoginForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        String loginUser = Global.DB_USER;
        String loginPasswd = Global.DB_PASS;
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        
        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
        out.println("<BODY><H1>BESTESTFAB</H1>");


        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              String userid = request.getParameter("uname");    
              String pwd = request.getParameter("pass");

              Class.forName("com.mysql.jdbc.Driver");
              Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "root", "pops711");
              Statement st = con.createStatement();
              // String query="select * from customers where email='" + userid + "' and password= '" + pwd + "'";
              // ResultSet rs = st.executeQuery(query);

              String selectSQL = "select * from customers where email='" + userid + "' and password='" + pwd + "'";
              PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
              // // preparedStatement.setInt(1, 1001);
              ResultSet rs = preparedStatement.executeQuery(selectSQL );
              // out.println(query);

              RequestDispatcher dispatcher = null;
              if (rs.next()) 
              {
                  User s= new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                  

            	  dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/main.jsp");
                  //out.println("<a href='logout.jsp'>Log out</a>");
                  session.setAttribute("user", s);
                  out.println("welcome " + session.getAttribute("user"));
                  dispatcher.forward(request, response);
              } 
              else 
              {
                  String redirectURL = "/WEB-INF/index.jsp?message=Invalid%20password%20or%20User%20Name%2E%20Try%20Again%2E";
            	  dispatcher = getServletContext().getRequestDispatcher(redirectURL);
                  dispatcher.forward(request, response);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}