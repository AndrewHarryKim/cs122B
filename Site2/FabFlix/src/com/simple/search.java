
package com.simple;

import java.io.Console;
import java.sql.*; // Enable SQL processing
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sun.rowset.CachedRowSetImpl;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String username = "root";
	static String password = "pops711";
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(dburl, username, password);
			
			stmt = conn.createStatement();
			
			if((request.getParameter("starname") == "" || request.getParameter("starname") == null) &&
				(request.getParameter("title") == "" || request.getParameter("title") == null) &&
				(request.getParameter("director") == "" || request.getParameter("director") == null) &&
				(request.getParameter("year") == "" || request.getParameter("year") == null))
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/search.jsp");
				if(dispatcher == null) 
				{
					System.out.println("oh no!");
				}
				dispatcher.forward(request, response);
			}
			else{
				//RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/MovieList");
				response.sendRedirect("/FabFlix/MovieList?"+request.getQueryString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
				{
					rs.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			} catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
