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
	    String star = request.getParameter("add-starname");
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
	    		star == null || ("".equals(star)) ||
	    		genre == null || ("".equals(genre)))
	    {
		    if( ("".equals(title)) )
		    	request.setAttribute("noTitle", true);
		    if( ("".equals(director)) )
		    	request.setAttribute("noDirector", true);
		    if( ("".equals(year)) )
		    	request.setAttribute("noYear", true);
		    if( ("".equals(star)) )
		    	request.setAttribute("noStar", true);
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
				cStmt = conn.prepareCall("{call add_movie(?, ?, ?, ?, ?, ?)}");
				
				String[] splt = star.split(" ");
				String fn = "";
				String ln = "";
				if (splt.length == 1) {
					ln = splt[0];
				} else if (splt.length == 2) {
					fn = splt[0];
					ln = splt[1];
				} else {

					fn = splt[0];
					ln = splt[1];
					for (int i = 2; i < splt.length; ++i)
						ln += " " + splt[i];
				}
				cStmt.setString(1, title);
				cStmt.setInt(2, yearNum);
				cStmt.setString(3, director);
				cStmt.setString(4, fn);
				cStmt.setString(5, ln);
				cStmt.setString(6, genre);
				cStmt.execute();
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
