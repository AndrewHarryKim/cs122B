
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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String dbname = "moviedb";
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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(dburl, username, password);
			
			stmt = conn.createStatement();
			
			PrintWriter out = response.getWriter();
			if((request.getParameter("name") == "" || request.getParameter("name") == null)&& 
					(request.getParameter("title") == ""|| request.getParameter("title") == null) && 
					(request.getParameter("year") == ""|| request.getParameter("year") == null) &&
					(request.getParameter("director") == ""|| request.getParameter("director") == null))
			{
				out.write("<h2>Hello!  Please enter a keyword</h2>");
				out.write("<input placeholder=\"Movie Title, star name, year, or director \" />");
				out.write("<input type=\"button\" value=\"submit\">");
			}
			else{
				rs = stmt.executeQuery("SELECT sm.movie_id, m.title, m.year, m.director, m.banner_url, m.trailer_url "
						+ " FROM movies m, stars s, " + "stars_in_movies sm "
						+ "WHERE s.id=sm.star_id AND m.id=sm.movie_id AND (s.last_name='" + request.getParameter("name")
						+ "' OR m.director='" + request.getParameter("name")
						+ "' OR m.title='" + request.getParameter("name")
						+ "' OR m.year='" + request.getParameter("name")
						+ "') GROUP BY sm.movie_id " + 
						"ORDER BY sm.movie_id;");
				while(rs.next())
				{
					out.write(
							"<p><img src=\"" +rs.getString(5) + "\" alt=\"movieBanner\" />\t" + 
							rs.getString(2) + "\t" + 
							rs.getString(3) + "\t" + 
							rs.getString(4) + "</p>");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn != null)
				{
						conn.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
				if(rs != null)
				{
					rs.close();
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
