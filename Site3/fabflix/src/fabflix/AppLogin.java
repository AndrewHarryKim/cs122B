package fabflix;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/AppLogin")
public class AppLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public AppLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
        Connection con = null;
        ResultSet rs = null;

		String email = request.getParameter("email");    
	    String pwd = request.getParameter("password");
	    
	    try {
	    	
	    	if(  (email != null && !("".equals(email))) &&
	    			(pwd != null && !("".equals(pwd))))
	    	{
				Class.forName("com.mysql.jdbc.Driver");
			    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", Global.DB_USER, Global.DB_PASS);
			    
		
			    PreparedStatement preparedStatement = con.prepareStatement("select * from customers where email = ? and password = ?;");
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
	    } catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
				{
						rs.close();
				}
				if(con != null)
				{
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
