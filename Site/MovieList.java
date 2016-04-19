package com.simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.rowset.CachedRowSetImpl;

/**
 * Servlet implementation class MovieList
 */
@WebServlet("/MovieList")
public class MovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String username = "root";
	static String password = "pops711";
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieList() {
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
			PrintWriter out = response.getWriter();
			
			
			String queryString = "SELECT sm.movie_id, m.title, m.year, m.director, m.banner_url, m.trailer_url "
					+ " FROM movies m, stars s, " + "stars_in_movies sm "
					+ "WHERE s.id=sm.star_id AND m.id=sm.movie_id";	
			if(request.getParameter("starname") != "" && request.getParameter("starname") != null)
			{
				String names[] = request.getParameter("starname").split(" ");
				if(names.length >= 2)
				{
					queryString += " AND s.first_name='" + names[0] + "' AND s.last_name='" + names[1] + "' ";
				}
				else
				{
					queryString += " AND (s.first_name='" + names[0] + "' OR s.last_name='" + names[0] + "') ";
				}
			}
			if(request.getParameter("title") != "" && request.getParameter("title") != null)
				queryString += " AND m.title=\"" + request.getParameter("title") + "\"";
			if(request.getParameter("director") != "" && request.getParameter("director") != null)
				queryString += " AND m.director='" + request.getParameter("director") + "'";
			if(request.getParameter("year") != "" && request.getParameter("year") != null)
				queryString += " AND m.year='" + request.getParameter("year") + "'";
			rs = stmt.executeQuery(queryString 
					+ " GROUP BY sm.movie_id " + 
					"ORDER BY sm.movie_id;");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-list.jsp");
			crs = new CachedRowSetImpl();
	        crs.populate(rs);
			request.getSession().setAttribute("rs", crs);
			dispatcher.include(request, response);
			request.getSession().setAttribute("rs", null);
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