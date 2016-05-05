package fabflix;

import java.io.IOException;
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

@WebServlet("/_dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Dashboard() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = null;
		RequestDispatcher dispatcher = null;
		Connection conn = null;
		ResultSet employeeMatches = null;
		PreparedStatement preparedStatement = null; 
		
		String email = request.getParameter("employee_email");
		String password = request.getParameter("employee_password");
		

	    String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		Boolean valid = Login.verify(gRecaptchaResponse);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", Global.DB_USER, Global.DB_PASS);
			session = request.getSession();
			if(email == null || "".equals(email))
			{
				dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/dashboard-login.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				preparedStatement = conn.prepareStatement("select e.password from employees e where email = ?;");
				preparedStatement.setString(1, email);
				
				employeeMatches = preparedStatement.executeQuery();
				
				if(employeeMatches.next() && valid)
				{
					if( employeeMatches.getString(1).equals(password))
					{
						dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp");
						dispatcher.forward(request, response);
					}
					else
					{
						request.setAttribute("dashboard_error_message", "Invalid username or password");
						dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/dashboard-login.jsp");
						dispatcher.forward(request, response);
					}
				}
				else
				{

					if(valid)
						request.setAttribute("dashboard_error_message", "Invalid username or password");
					else
						request.setAttribute("dashboard_error_message", "Invalid reCAPTCHA, you must be a robot");
					dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/dashboard-login.jsp");
					dispatcher.forward(request, response);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
