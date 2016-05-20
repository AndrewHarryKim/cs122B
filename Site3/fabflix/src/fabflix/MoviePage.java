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
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;


@WebServlet("/MoviePage")
public class MoviePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";

    public MoviePage() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("email") == null) || ("".equals(session.getAttribute("email")))) {
			String redirectURL = request.getContextPath() + Global.loginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
		
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
			conn = DriverManager.getConnection(dburl, Global.DB_USER, Global.DB_PASS);
			
			
			if(request.getParameter("addToCart") != null && !request.getParameter("addToCart").equals(""))
			{
				CartList cart = (CartList)request.getSession().getAttribute("cart");
				prepStmt = conn.prepareStatement("select * from movies m where m.id= ? ;");
				prepStmt.setString(1, request.getParameter("addToCart"));
				cartQuery = prepStmt.executeQuery();
				
				cartQuery.first();
				CartItem cartItem = new CartItem(cartQuery.getInt(1), 
													cartQuery.getString(2),
													cartQuery.getInt(3),
													cartQuery.getString(4),
													cartQuery.getString(5),
													cartQuery.getString(6));
				cart.addToCart(cartItem);
				request.getSession().setAttribute("cart", cart);
				
				prepStmt.close();
				prepStmt = null;
			}
			if(request.getParameter("movieid") !=null && request.getParameter("movieid") != "")
			{
				String movieid = request.getParameter("movieid");
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
				
				RequestDispatcher dispatcher = null;
				
				if(request.getParameter("addToCart") != null && !request.getParameter("addToCart").equals(""))
				{
					dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-page.jsp?addToCart=" + request.getParameter("addToCart"));
				}
				else
				{
					dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-page.jsp");
				}

				request.setAttribute("movie_id", crs.getInt(1));
				request.setAttribute("title", crs.getString(2));
				request.setAttribute("year", crs.getString(3));
				request.setAttribute("director", crs.getString(4));
				request.setAttribute("banner_url", crs.getString(5));
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
