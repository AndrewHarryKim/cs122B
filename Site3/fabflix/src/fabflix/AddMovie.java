package fabflix;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			try {
				conn = DriverManager.getConnection(Global.dburl, Global.DB_USER, Global.DB_PASS);
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

				request.setAttribute("add_movie_message", "Movie: " + title + " (" );
				if("no".equals(cStmt.getString(7)))
					request.setAttribute("add_movie_message", request.getAttribute("add_movie_message") + "not ");
				request.setAttribute("add_movie_message", request.getAttribute("add_movie_message") + "added)<br/> ");
				
				request.setAttribute("add_movie_message", 
						request.getAttribute("add_movie_message") + "star: " + starFirst + " " + starLast + " (");
				if("no".equals(cStmt.getString(8)))
					request.setAttribute("add_movie_message", request.getAttribute("add_movie_message") + "not ");
				request.setAttribute("add_movie_message", request.getAttribute("add_movie_message") + "added)<br/> ");
				
				request.setAttribute("add_movie_message", request.getAttribute("add_movie_message") + "genre: " + genre + " (");
				if("no".equals(cStmt.getString(9)))
					request.setAttribute("add_movie_message", request.getAttribute("add_movie_message") + "not ");
				request.setAttribute("add_movie_message", request.getAttribute("add_movie_message") + "added)<br/> ");
	    	} catch (SQLException e1) {
				e1.printStackTrace();
			}
	    }
	    
	    
	    
	    
	    
	    
		
		
		
		dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/add-movie.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
