package fabflix;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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


@WebServlet("/MovieHover")
public class MovieHover extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public MovieHover() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		ResultSet starList = null;
		ResultSet genreList = null;
		ResultSet cartQuery = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			crs = new CachedRowSetImpl();
			conn = DriverManager.getConnection(Global.dburl, Global.DB_USER, Global.DB_PASS);
		
			if(request.getParameter("id") !=null && request.getParameter("id") != "")
			{
				String movieid = request.getParameter("id");
				prepStmt = conn.prepareStatement("SELECT m.id, m.title, m.year, m.director, m.banner_url" +
						" FROM movies m" +
						" WHERE m.id= ?;");
				prepStmt.setString(1, movieid);
				rs = prepStmt.executeQuery();
				crs.populate(rs);
				crs.first();
				prepStmt.close();
				prepStmt = null;
				
				prepStmt = conn.prepareStatement("SELECT g.name, g.id" + 
						" FROM genres g, movies m, genres_in_movies gm" +
						" WHERE g.id = gm.genre_id AND m.id = gm.movie_id AND m.id=?;");
				prepStmt.setInt(1, crs.getInt(1));
				genreList = prepStmt.executeQuery();
				ArrayList<String> genreNames = new ArrayList<String>();
				ArrayList<Integer> genreIDs = new ArrayList<Integer>();
				while(genreList.next())
				{
					genreNames.add(genreList.getString(1));
					genreIDs.add((Integer)genreList.getInt(2));
				}
				prepStmt.close();
				prepStmt = null;
				
				prepStmt = conn.prepareStatement("SELECT s.first_name, s.last_name, s.id" + 
						" FROM stars s, movies m, stars_in_movies sm" +
						" WHERE s.id = sm.star_id AND m.id = sm.movie_id AND m.id=?;");
				prepStmt.setInt(1, crs.getInt(1));
				starList = prepStmt.executeQuery();
				ArrayList<String> starNames = new ArrayList<String>();
				ArrayList<Integer> starIDs = new ArrayList<Integer>();
				while(starList.next())
				{
					starNames.add(starList.getString(1)+ " " + starList.getString(2));
					starIDs.add((Integer)starList.getInt(3));
					
				}

				prepStmt.close();
				prepStmt = null;
				

				Integer movie_id = crs.getInt(1);
				String title = crs.getString(2);
				String year = crs.getString(3);
				String director = crs.getString(4);
				String banner_url = crs.getString(5);
				
				PrintWriter out = response.getWriter();
				out.append("<div>");
				out.append("	<div><img src='" + banner_url + "' alt='" + title + " Photo'/></div>");
				out.append("	<div>");
				out.append("		<div><div class='starInfoHeader'>Movie ID#:</div> " + movie_id + "");
				out.append("		</div>");
				out.append("		<div>Title: " + title + "</div>");
				out.append("		<div>Year of Release: " + year + "</div>");
				out.append("		<div>Director: " + director + "</div>");
				out.append("		<div>Genres:</div>");
				out.append("		<div>");
				out.append("			<div class='indent'>");
										for(int i = 0;  i < genreNames.size(); i++) {
				out.append("					<a href='" + getServletContext() + "/MovieList?type=genre&id=" + genreIDs.get(i) + "'>" + genreNames.get(i) + "</a>");
				out.append("					</div><div class='indent'>");
										}
				out.append("			</div>");
				out.append("		</div> <br/>");
				out.append("		<div>Stars:</div>");
				out.append("		<div>");
				out.append("			<div class='indent'>");
											for(int i = 0;  i < starNames.size(); i++) {
				out.append("					<a href='" + getServletContext() + "/StarPage?starname=" + starNames.get(i) + "'>" + starNames.get(i) + "</a>");
				out.append("					</div><div class='indent'>");
											}
				out.append("			</div>");
				out.append("		</div>");
				out.append("	</div>");
				out.append("</div>");
			}
			else
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/empty-star-page.jsp");
				dispatcher.forward(request, response);
			}
			response.getWriter().append("Served at: ").append(request.getContextPath());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(cartQuery != null)
				{
					cartQuery.close();
				}
				if(starList != null)
				{
					starList.close();
				}
				if(genreList != null)
				{
					genreList.close();
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
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
