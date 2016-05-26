package fabflix;

import java.io.Console;
import java.sql.*; // Enable SQL processing
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.sun.rowset.CachedRowSetImpl;

@WebServlet("/CheckCC")
public class CheckCC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String username = Global.DB_USER;
	static String password = Global.DB_PASS;

	public CheckCC() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("email") == null) || ("".equals(session.getAttribute("email")))) {
			String redirectURL = request.getContextPath() + Global.loginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";
		
		
		PrintWriter out = response.getWriter();
		try {

			initialContext = new InitialContext();
			environmentContext = (Context) initialContext.lookup("java:comp/env");
			dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();

			stmt = conn.createStatement();
			String cc_id = request.getParameter("cc_id");
			String first_name = request.getParameter("first_name");
			String last_name = request.getParameter("last_name");
			String expiration = request.getParameter("expiration");
			 rs = stmt.executeQuery("SELECT *" + " FROM creditcards"
			 + " WHERE first_name='" + first_name + "' AND last_name='" +
			 last_name  + "' AND id='" + cc_id + "' AND expiration='" + expiration +"';");

			 if(rs.next())
			 {
				 // This means you did well
				 String Message=("Successful%20Transaction%2E%20Redirecting%20in%205%20Seconds%2E");
				 out.println(Message);
				 
				 response.sendRedirect("Success"+"?message="+Message);
			 }
			 else
			 {
				 System.out.println(cc_id+"\n"+cc_id+"\n"+first_name+"\n"+last_name+"\n"+expiration);
				 response.sendRedirect(Global.checkoutServletPath+"?confirmed=False");
				 
			 }
			//
			//
			// RequestDispatcher dispatcher =
			// getServletContext().getRequestDispatcher("/WEB-INF/star-page.jsp");
			//
			// request.setAttribute("star_id", rs.getInt(1));
			// request.setAttribute("star", rs.getString(2) + " " +
			// rs.getString(3));
			// request.setAttribute("dob", rs.getString(4));
			// request.setAttribute("photo", rs.getString(5));
			// request.setAttribute("movieTitles", movieTitles);
			// request.setAttribute("movieIDs", movieIDs);
			// dispatcher.forward(request, response);
			// }
			// else
			// {
			// RequestDispatcher dispatcher =
			// getServletContext().getRequestDispatcher("/WEB-INF/empty-star-page.jsp");
			// dispatcher.forward(request, response);
			// }
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
