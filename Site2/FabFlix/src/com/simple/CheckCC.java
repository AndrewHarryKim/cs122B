package com.simple;

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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

@WebServlet("/CheckCC")
public class CheckCC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String username = Global.DB_USER;
	static String password = Global.DB_PASS;
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckCC() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet movieList = null;
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			conn = DriverManager.getConnection(dburl, username, password);

			stmt = conn.createStatement();
			stmt2 = conn.createStatement();

			// if(request.getParameter("cart") !=null)
			// {
			//
			// out.println("THE CHECKOUT");
			// <td><input type="text" name="cc_id" value="" /></td>
			// </tr>
			// <tr>
			// <td>First Name</td>
			// <td><input type="text" name="first_name" value="" /></td>
			// </tr>
			// <tr>
			// <td>Last Name</td>
			// <td><input type="text" name="last_name" value="" /></td>
			// </tr>
			// <tr>
			// <td>Exp Date</td>
			// <td><input type="date" name="expiration" value="" /></td>
			// </tr>
			String cc_id = request.getParameter("cc_id");
			String first_name = request.getParameter("first_name");
			String last_name = request.getParameter("last_name");
			String expiration = request.getParameter("expiration");
			 rs = stmt.executeQuery("SELECT *" + " FROM creditcards"
			 + " WHERE first_name='" + first_name + "' AND last_name='" +
			 last_name  + "' AND id='" + cc_id + "' AND expiration='" + expiration +"';");

			// ArrayList<String> movieTitles = new ArrayList<String>();
			// ArrayList<Integer> movieIDs = new ArrayList<Integer>();
			 if(rs.next())
			 {
				 // This means you did well
				 String Message=("Successful Transaction. Redirecting in 5 Seconds.");
				 out.println(Message);
				 
				 response.sendRedirect("Success"+"?message="+Message);
			 }
			 else
			 {
				 System.out.println(cc_id+"\n"+cc_id+"\n"+first_name+"\n"+last_name+"\n"+expiration);
				 response.sendRedirect(Global.checkoutPath+"?confirmed=False");
				 
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}