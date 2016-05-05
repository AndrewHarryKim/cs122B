package fabflix;

import java.sql.*; // Enable SQL processing

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";

 
    public Search() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
			String redirectURL = request.getContextPath() + Global.loginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
		
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, Global.DB_USER, Global.DB_PASS);
			stmt = conn.createStatement();
			
			if((request.getParameter("starname") == "" || request.getParameter("starname") == null) &&
				(request.getParameter("title") == "" || request.getParameter("title") == null) &&
				(request.getParameter("director") == "" || request.getParameter("director") == null) &&
				(request.getParameter("year") == "" || request.getParameter("year") == null))
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/search.jsp");
				dispatcher.forward(request, response);
			}
			else{
				String redirectURL = request.getContextPath() + "/MovieList?" +request.getQueryString();
				response.sendRedirect(redirectURL);
			}
		} catch (SQLException | ClassNotFoundException e) {
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
