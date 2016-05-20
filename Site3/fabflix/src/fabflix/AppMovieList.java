package fabflix;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


@WebServlet("/AppMovieList")
public class AppMovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int STRING_ARG_TYPE = 0;
	private static final int INT_ARG_TYPE = 1;
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";
       
    public AppMovieList() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ArrayList<String> argumentList = new ArrayList<String>();
		
		JSONObject jsonIn = new JSONObject(request.getParameter("json"));
		JSONObject jsonOut = new JSONObject();
		String title = jsonIn.getString("title");
		String[] tokens;
		if(title != null && !"".equals(title))
		{
			try {	
	
				
				
				/*************************   Establish SQL Connection  *****************************/
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				conn = DriverManager.getConnection(dburl, Global.DB_USER, Global.DB_PASS);
				
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
	
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		response.getWriter().write(jsonOut.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
