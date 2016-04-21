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
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet starList = null;
		ResultSet genreList = null;
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
			String queryString = "";
			
			if(request.getParameter("type") != null && !request.getParameter("type").equals(""))
			{
				if(request.getParameter("type").equals("genre"))
				{
					queryString = "SELECT sm.movie_id, m.title, m.year, m.director"
							+ " FROM movies m, stars s,stars_in_movies sm, genres_in_movies gm"
							+ " WHERE s.id=sm.star_id AND m.id=sm.movie_id AND m.id=gm.movie_id AND gm.genre_id = " + request.getParameter("id");
				}
				else if (request.getParameter("type").equals("title"))
				{
					queryString = "SELECT sm.movie_id, m.title, m.year, m.director"
							+ " FROM movies m, stars s, " + "stars_in_movies sm"
							+ " WHERE s.id=sm.star_id AND m.id=sm.movie_id AND m.title LIKE \"" + request.getParameter("browseletter") + "%\"";
				}
			}
			else if((request.getParameter("starname") != null && request.getParameter("starname") != "") ||
					(request.getParameter("year") != null && request.getParameter("year") != "") ||
					(request.getParameter("title") != null && request.getParameter("title") != "")||
					(request.getParameter("director") != null && request.getParameter("director") != ""))
			{
				
				Boolean mainSearch = false;
				if(request.getParameter("starname") != null){
					if(request.getParameter("starname").equals(request.getParameter("director"))&&
							request.getParameter("starname").equals(request.getParameter("year"))&&
							request.getParameter("starname").equals(request.getParameter("title")))
					{
						mainSearch = true;
					}
				}
				queryString = "SELECT sm.movie_id, m.title, m.year, m.director"
						+ " FROM movies m, stars s, " + "stars_in_movies sm"
						+ " WHERE s.id=sm.star_id AND m.id=sm.movie_id";
				if(mainSearch){queryString += " AND (";}
				String queryHelper;
				if(request.getParameter("starname") != "" && request.getParameter("starname") != null)
				{
					String names[] = request.getParameter("starname").split(" ");
					if(names.length >= 2)
					{
						if(!mainSearch){queryString += " AND";}
						queryString += " (s.first_name LIKE '%" + names[0] + "%' ";
						for(int i = 0; i < names[0].length(); i++)
						{
							queryString += " OR s.first_name LIKE \"%" + names[0].substring(0,i) + "_" + names[0].substring(i) + "%\"";
						}
						queryString += " OR s.first_name LIKE \"%" + names[0].substring(0,names[0].length()-1) + "%\"";
						for(int i = 1; i < names[0].length(); i++)
						{
							queryString += " OR s.first_name LIKE \"%" + names[0].substring(0,i-1) + names[0].substring(i) + "%\"";
						}
						queryString += " OR s.first_name LIKE \"%" + names[0].substring(1) + "%\"";
						queryString += ")";
						
						queryString += " AND (s.last_name LIKE '%" + names[1] + "%' ";
						for(int i = 0; i < names[1].length(); i++)
						{
							queryString += " OR s.last_name LIKE \"%" + names[1].substring(0,i) + "_" + names[1].substring(i) + "%\"";
						}
						queryString += " OR s.last_name LIKE \"%" + names[1].substring(0,names[1].length()-1) + "%\"";
						for(int i = 1; i < names[1].length(); i++)
						{
							queryString += " OR s.last_name LIKE \"%" + names[1].substring(0,i-1) + names[1].substring(i) + "%\"";
						}
						queryString += " OR s.last_name LIKE \"%" + names[1].substring(1) + "%\"";
						queryString += ")";
						if(mainSearch){queryString += " OR";}
					}
					else
					{
						if(!mainSearch){queryString += " AND";}
						queryString += " (s.first_name LIKE '%" + names[0] + "%' ";
						for(int i = 0; i < names[0].length(); i++)
						{
							queryString += " OR s.first_name LIKE \"%" + names[0].substring(0,i) + "_" + names[0].substring(i) + "%\"";
						}
						queryString += " OR s.first_name LIKE \"%" + names[0].substring(0,names[0].length()-1) + "%\"";
						for(int i = 1; i < names[0].length(); i++)
						{
							queryString += " OR s.first_name LIKE \"%" + names[0].substring(0,i-1) + names[0].substring(i) + "%\"";
						}
						queryString += " OR s.first_name LIKE \"%" + names[0].substring(1) + "%\"";
						
						queryString += " OR s.last_name LIKE '%" + names[0] + "%' ";
						for(int i = 0; i < names[0].length(); i++)
						{
							queryString += " OR s.last_name LIKE \"%" + names[0].substring(0,i) + "_" + names[0].substring(i) + "%\"";
						}
						queryString += " OR s.last_name LIKE \"%" + names[0].substring(0,names[0].length()-1) + "%\"";
						for(int i = 1; i < names[0].length(); i++)
						{
							queryString += " OR s.last_name LIKE \"%" + names[0].substring(0,i-1) + names[0].substring(i) + "%\"";
						}
						queryString += " OR s.last_name LIKE \"%" + names[0].substring(1) + "%\"";
						queryString += ")";
						if(mainSearch){queryString += " OR";}
					}
				}
				
				if(request.getParameter("title") != "" && request.getParameter("title") != null)
				{
					queryHelper = request.getParameter("title");
					if(!mainSearch){queryString += " AND";}
					queryString += " (m.title LIKE \"%" + queryHelper + "%\"";
					for(int i = 0; i < queryHelper.length(); i++)
					{
						queryString += " OR m.title LIKE \"%" + queryHelper.substring(0,i) + "_" + queryHelper.substring(i) + "%\"";
					}
					queryString += " OR m.title LIKE \"%" + queryHelper.substring(0,queryHelper.length()-1) + "%\"";
					for(int i = 1; i < queryHelper.length(); i++)
					{
						queryString += " OR m.title LIKE \"%" + queryHelper.substring(0,i-1) + queryHelper.substring(i) + "%\"";
					}
					queryString += " OR m.title LIKE \"%" + queryHelper.substring(1) + "%\"";
					queryString += ")";
					if(mainSearch){queryString += " OR";}
				}
				if(request.getParameter("director") != "" && request.getParameter("director") != null)
				{
					queryHelper = request.getParameter("director");
					if(!mainSearch){queryString += " AND";}
					queryString += " (m.director LIKE \"%" + queryHelper + "%\"";
					for(int i = 0; i < queryHelper.length(); i++)
					{
						queryString += " OR m.director LIKE \"%" + queryHelper.substring(0,i) + "_" + queryHelper.substring(i) + "%\"";
					}
					queryString += " OR m.director LIKE \"%" + queryHelper.substring(0,queryHelper.length()-1) + "%\"";
					for(int i = 1; i < queryHelper.length(); i++)
					{
						queryString += " OR m.director LIKE \"%" + queryHelper.substring(0,i-1) + queryHelper.substring(i) + "%\"";
					}
					queryString += " OR m.director LIKE \"%" + queryHelper.substring(1) + "%\"";
					queryString += ")";
					if(mainSearch){queryString += " OR";}
				}
				if(request.getParameter("year") != "" && request.getParameter("year") != null)
				{
					if(!mainSearch){queryString += " AND";}
					queryString += " m.year='" + request.getParameter("year") + "'";
				}

				if(mainSearch){queryString += ") ";}
				
			}
			else
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-list.jsp");
				dispatcher.include(request, response);
			}
			
			int offset = 0;
			int pageSize = 10;
			if(request.getParameter("pagesize")!=null)
			{
				if(!request.getParameter("pagesize").equals(""))
				{
					if(Integer.parseInt(request.getParameter("pagesize"))>0 && Integer.parseInt(request.getParameter("pagesize"))<100)
					{
						pageSize = Integer.parseInt(request.getParameter("pagesize"));
					}
				}
			}
			if(request.getParameter("pagenum")!=null)
			{
				if(!request.getParameter("pagenum").equals(""))
				{
					if(Integer.parseInt(request.getParameter("pagenum"))>0  && Integer.parseInt(request.getParameter("pagenum"))<100)
					{
						offset = pageSize*(Integer.parseInt(request.getParameter("pagenum"))-1);
					}
				}
			}
			rs = stmt.executeQuery(queryString 
					+ " GROUP BY sm.movie_id " + 
					"ORDER BY sm.movie_id LIMIT " + pageSize +  " OFFSET " + offset + ";");
			
			ArrayList<ArrayList<String>> moviesAndStars = new ArrayList<ArrayList<String>>();
			ArrayList<String> starNames;

			stmt2 = conn.createStatement();
			while(rs.next())
			{
				starList = stmt2.executeQuery("select s.first_name, s.last_name"
						+ " FROM stars s, " + "stars_in_movies sm"
					+ " WHERE s.id=sm.star_id AND sm.movie_id=" + rs.getInt(1) + ";");
				
				starNames = new ArrayList<String>();
				while(starList.next())
				{
					starNames.add(starList.getString(1)+ " " + starList.getString(2));
				}
				moviesAndStars.add(starNames);
				starList.close();
			}
			rs.beforeFirst();
			
			ArrayList<ArrayList<String>> moviesAndGenres = new ArrayList<ArrayList<String>>();
			ArrayList<String> genres;
			ArrayList<ArrayList<Integer>> moviesAndGenreIDs = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> genreIDs;
			
			stmt2 = conn.createStatement();
			while(rs.next())
			{
				genreList = stmt2.executeQuery("select g.name, g.id" +
							" FROM genres g, genres_in_movies gm" +
							" WHERE g.id=gm.genre_id AND gm.movie_id=" + rs.getInt(1) + ";");
				
				genres = new ArrayList<String>();
				genreIDs = new ArrayList<Integer>();
				genreList.beforeFirst();
				while(genreList.next())
				{
					genres.add(genreList.getString(1));
					genreIDs.add((Integer)genreList.getInt(2));
				}
				moviesAndGenres.add(genres);
				moviesAndGenreIDs.add(genreIDs);
				genreList.close();
			}
			rs.beforeFirst();
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-list.jsp");
			crs = new CachedRowSetImpl();
	        crs.populate(rs);
	        
			request.getSession().setAttribute("rs", crs);
			request.getSession().setAttribute("moviesAndStars", moviesAndStars);
			request.getSession().setAttribute("moviesAndGenres", moviesAndGenres);
			request.getSession().setAttribute("moviesAndGenreIDs", moviesAndGenreIDs);
			dispatcher.include(request, response);
			request.getSession().setAttribute("moviesAndGenreIDs", null);
			request.getSession().setAttribute("moviesAndGenres", null);
			request.getSession().setAttribute("moviesAndStars", null);
			request.getSession().setAttribute("rs", null);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(starList != null)
				{
					starList.close();
				}
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