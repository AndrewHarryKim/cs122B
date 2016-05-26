package fabflix;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;


@WebServlet("/AppMovieList")
public class AppMovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AppMovieList() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		
		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";
		
		
		JSONObject jsonIn = new JSONObject(request.getParameter("json"));
		JSONObject jsonOut = new JSONObject();
		String title = jsonIn.getString("title");
		String[] tokens;
		if(title != null && !"".equals(title))
		{
			try {	
	
				
				
				/*************************   Establish SQL Connection  *****************************/

				initialContext = new InitialContext();
				environmentContext = (Context) initialContext.lookup("java:comp/env");
				dataSource = (DataSource) environmentContext.lookup(dataResourceName);
				conn = dataSource.getConnection();
				
				tokens = title.split(" ");

				String titleSearch = "%"+tokens[0]+"%";

				prepStmt = conn.prepareStatement("SELECT m.id, m.title "
						+ " FROM movies m, stars s, " + "stars_in_movies sm"
						+ " WHERE s.id=sm.star_id AND m.id=sm.movie_id AND m.title LIKE ? ");
				for(int i = 1; i < tokens.length; i++)
				{
					titleSearch += tokens[i] + "%";
				}

				prepStmt.setString(1, titleSearch);
				rs = prepStmt.executeQuery();
				while(rs.next())
				{
	
					jsonOut.put("movie"+rs.getInt(1), rs.getString(2));
				}
	
			} catch (SQLException | NamingException e){
				e.printStackTrace();
			} finally {
				try {
					if(rs != null)
					{
						rs.close();
					}
					rs = null;
					if(conn != null)
					{
						conn.close();
					}
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		response.getWriter().write(jsonOut.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
