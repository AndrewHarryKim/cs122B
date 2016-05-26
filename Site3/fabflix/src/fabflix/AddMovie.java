package fabflix;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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


@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddMovie() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("employee_email") == null) || ("".equals(session.getAttribute("employee_email")))) {
			String redirectURL = request.getContextPath() + Global.dashboardLoginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
	    

		RequestDispatcher dispatcher = null;
		CallableStatement cStmt = null;
		Connection conn = null;
		
		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";
		
		
		
		
	    String title = request.getParameter("add-movie-title");
	    String director = request.getParameter("add-movie-director");
	    String year = request.getParameter("add-movie-year");
	    String starFirst = request.getParameter("add-starfirstname");
	    String starLast = request.getParameter("add-starlastname");
	    String genre = request.getParameter("add-genrename");
	    
	    Integer yearNum = null;
	    try{
	    	yearNum  = Integer.parseInt(year);
	    } catch (NumberFormatException e){
	    	e.printStackTrace();
	    }
	    
	    if(title == null || ("".equals(title)) ||
	    		director == null || ("".equals(director)) ||
	    		year == null || ("".equals(year)) ||
	    		starFirst == null || ("".equals(starFirst)) ||
	    		starLast == null || ("".equals(starLast)) ||
	    		genre == null || ("".equals(genre)))
	    {
		    if( ("".equals(title)) )
		    	request.setAttribute("noTitle", true);
		    if( ("".equals(director)) )
		    	request.setAttribute("noDirector", true);
		    if( ("".equals(year)) )
		    	request.setAttribute("noYear", true);
		    if( ("".equals(starFirst)) )
		    	request.setAttribute("noStarFirst", true);
		    if( ("".equals(starLast)) )
		    	request.setAttribute("noStarLast", true);
		    if( ("".equals(genre)) )
		    	request.setAttribute("noGenre", true);
		    dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/add-movie.jsp");
			dispatcher.forward(request, response);
			return;
	    }
	    else if(yearNum != null)
	    {
	    	

			try {
				initialContext = new InitialContext();
				environmentContext = (Context) initialContext.lookup("java:comp/env");
				dataSource = (DataSource) environmentContext.lookup(dataResourceName);
				conn = dataSource.getConnection();
				cStmt = conn.prepareCall("{call add_movie(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
				
		
				cStmt.setString(1, title);
				cStmt.setInt(2, yearNum);
				cStmt.setString(3, director);
				cStmt.setString(4, starFirst);
				cStmt.setString(5, starLast);
				cStmt.setString(6, genre);
				cStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
				cStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
				cStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
				cStmt.execute();

				String message = "Movie: " + title + " (";
				if("no".equals(cStmt.getString(7)))
					message += "already exiests, not ";
				message += "added)<br/> ";
				
				message +=  "star: " + starFirst + " " + starLast + " (";
				if("no".equals(cStmt.getString(8)))
					message += "already exiests, not ";
				message += "added)<br/> ";
				
				message += "genre: " + genre + " (";
				if("no".equals(cStmt.getString(9)))
					message += "already exiests, not ";
				message += "added)<br/> ";

				request.setAttribute("add_movie_message", message);
	    	} catch (SQLException | NamingException e1) {
				e1.printStackTrace();
			} finally {

				try {
					if(cStmt != null)
					{
							cStmt.close();
					}
					cStmt = null;
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
	    else
	    {
			request.setAttribute("add_movie_message", "Please enter a number for year");
	    }
	    
	    
	    
	    

		
		
		
		dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/add-movie.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
