package fabflix;

import fabflix.Global;

import java.io.IOException;
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


@WebServlet("/StarPage")
public class StarPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";
       

    public StarPage() {
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
		Statement stmt2 = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		ResultSet movieList = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(dburl, Global.DB_USER, Global.DB_PASS);
			
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			crs = new CachedRowSetImpl();
			

			if(request.getParameter("starname") != null && request.getParameter("starname") != "")
			{
				String[] names = request.getParameter("starname").split(" ");
				prepStmt = conn.prepareStatement("SELECT s.id, s.first_name, s.last_name, s.dob, s.photo_url" +
						" FROM stars s" +
						" WHERE s.first_name=? AND  s.last_name=?;");
				prepStmt.setString(1, names[0]);
				prepStmt.setString(2, names[1]);
				rs = prepStmt.executeQuery();
				crs.populate(rs);
				crs.first();
				prepStmt.close();
				prepStmt = null;

				prepStmt = conn.prepareStatement("SELECT m.title, m.id" + 
						" FROM stars s, movies m, stars_in_movies sm" +
						" WHERE s.id = sm.star_id AND m.id = sm.movie_id AND s.id=?;");
				prepStmt.setInt(1, crs.getInt(1));
				movieList = prepStmt.executeQuery();
				ArrayList<String> movieTitles = new ArrayList<String>();
				ArrayList<Integer> movieIDs = new ArrayList<Integer>();
				while(movieList.next())
				{
					movieTitles.add(movieList.getString(1));
					movieIDs.add((Integer)movieList.getInt(2));
				}
				prepStmt.close();
				prepStmt = null;
				
				
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/star-page.jsp");

				request.setAttribute("star_id", crs.getInt(1));
				request.setAttribute("star", crs.getString(2) + " " + crs.getString(3));
				request.setAttribute("dob", crs.getString(4));
				request.setAttribute("photo", crs.getString(5));
				request.setAttribute("movieTitles", movieTitles);
				request.setAttribute("movieIDs", movieIDs);
				dispatcher.forward(request, response);
			}
			else
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/empty-star-page.jsp");
				dispatcher.forward(request, response);
				return;
			}
		} catch (SQLException e) {
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
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
