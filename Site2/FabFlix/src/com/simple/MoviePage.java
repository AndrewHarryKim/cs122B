package com.simple;

import java.io.IOException;
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
 * Servlet implementation class MoviePage
 */
@WebServlet("/MoviePage")
public class MoviePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String username = "root";
	static String password = "pops711";
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoviePage() {
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
		ResultSet starList = null;
		ResultSet genreList = null;
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
			

			if(request.getParameter("movieid") !=null && request.getParameter("movieid") != "")
			{
				String movieid = request.getParameter("movieid");
				rs = stmt.executeQuery("SELECT m.id, m.title, m.year, m.director, m.banner_url" +
						" FROM movies m" +
						" WHERE m.id='" + movieid + "';");
				rs.first();
				
				genreList = stmt2.executeQuery("SELECT g.name, g.id" + 
						" FROM genres g, movies m, genres_in_movies gm" +
						" WHERE g.id = gm.genre_id AND m.id = gm.movie_id AND m.id=" + rs.getInt(1) + ";");
				ArrayList<String> genreNames = new ArrayList<String>();
				ArrayList<Integer> genreIDs = new ArrayList<Integer>();
				while(genreList.next())
				{
					genreNames.add(genreList.getString(1));
					genreIDs.add((Integer)genreList.getInt(2));
					
				}
				starList = stmt2.executeQuery("SELECT s.first_name, s.last_name, s.id" + 
						" FROM stars s, movies m, stars_in_movies sm" +
						" WHERE s.id = sm.star_id AND m.id = sm.movie_id AND m.id=" + rs.getInt(1) + ";");
				ArrayList<String> starNames = new ArrayList<String>();
				ArrayList<Integer> starIDs = new ArrayList<Integer>();
				while(starList.next())
				{
					starNames.add(starList.getString(1)+ " " + starList.getString(2));
					starIDs.add((Integer)starList.getInt(3));
					
				}
				
				
				
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-page.jsp");

				request.setAttribute("movie_id", rs.getInt(1));
				request.setAttribute("title", rs.getString(2));
				request.setAttribute("year", rs.getString(3));
				request.setAttribute("director", rs.getString(4));
				request.setAttribute("banner_url", rs.getString(5));
				request.setAttribute("genreNames", genreNames);
				request.setAttribute("genreIDs", genreIDs);
				request.setAttribute("starNames", starNames);
				request.setAttribute("starIDs", starIDs);
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
