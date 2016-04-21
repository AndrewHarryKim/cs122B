package com.simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.rowset.CachedRowSetImpl;

/**
 * Servlet implementation class StarPage
 */
@WebServlet("/StarPage")
public class StarPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String username = "root";
	static String password = "pops711";
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StarPage() {
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
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet movieList = null;
		
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
			

			if(request.getParameter("starname") !=null && request.getParameter("starname") != "")
			{
				String[] names = request.getParameter("starname").split(" ");
				rs = stmt.executeQuery("SELECT s.id, s.first_name, s.last_name, s.dob, s.photo_url" +
						" FROM stars s" +
						" WHERE s.first_name='" + names[0] + "' AND  s.last_name='" + names[1] + "';");
				rs.next();
				movieList = stmt2.executeQuery("SELECT m.title, m.id" + 
						" FROM stars s, movies m, stars_in_movies sm" +
						" WHERE s.id = sm.star_id AND m.id = sm.movie_id AND s.id=" + rs.getInt(1) + ";");
				ArrayList<String> movieTitles = new ArrayList<String>();
				ArrayList<Integer> movieIDs = new ArrayList<Integer>();
				while(movieList.next())
				{
					movieTitles.add(movieList.getString(1));
					movieIDs.add((Integer)movieList.getInt(2));
					
				}
				
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/star-page.jsp");

				request.setAttribute("star_id", rs.getInt(1));
				request.setAttribute("star", rs.getString(2) + " " + rs.getString(3));
				request.setAttribute("dob", rs.getString(4));
				request.setAttribute("photo", rs.getString(5));
				request.setAttribute("movieTitles", movieTitles);
				request.setAttribute("movieIDs", movieIDs);
				dispatcher.forward(request, response);
			}
			else
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/empty-star-page.jsp");
				dispatcher.forward(request, response);
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
