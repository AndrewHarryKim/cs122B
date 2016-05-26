package fabflix;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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


@WebServlet("/AppLogin")
public class AppLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public AppLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
        Connection conn = null;
        ResultSet rs = null;
		
		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";

		String email = request.getParameter("email");    
	    String pwd = request.getParameter("password");
	    
	    try {
	    	
	    	if(  (email != null && !("".equals(email))) &&
	    			(pwd != null && !("".equals(pwd))))
	    	{
				initialContext = new InitialContext();
				environmentContext = (Context) initialContext.lookup("java:comp/env");
				dataSource = (DataSource) environmentContext.lookup(dataResourceName);
				conn = dataSource.getConnection();
			    
		
			    PreparedStatement preparedStatement = conn.prepareStatement("select * from customers where email = ? and password = ?;");
			    preparedStatement.setString(1, email );
			    preparedStatement.setString(2, pwd );

			    rs = preparedStatement.executeQuery();
			    if (rs.next()) 
			    {
			    	out.write("true");
			    } 
			    else 
			    {
			    	out.write("Invalid Username or Password. Try Again");
			    }
	    	}
		    else 
		    {
		    	out.write("Please enter a username and password");
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
