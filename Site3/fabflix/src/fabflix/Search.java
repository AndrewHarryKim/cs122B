package fabflix;

import java.sql.*; // Enable SQL processing

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

 
    public Search() {
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
		ResultSet rs = null;
		
		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";
		
		try {
			initialContext = new InitialContext();
			environmentContext = (Context) initialContext.lookup("java:comp/env");
			dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
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
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
				{
					rs.close();
				}
				rs = null;
				if(stmt != null)
				{
					stmt.close();
				}
				stmt = null;
				if(conn != null)
				{
					conn.close();
				}
				conn = null;
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

