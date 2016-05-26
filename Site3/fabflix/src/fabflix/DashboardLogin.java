package fabflix;

import java.io.IOException;
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

@WebServlet("/_dashboard")
public class DashboardLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DashboardLogin() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = null;
		RequestDispatcher dispatcher = null;
		Connection conn = null;
		ResultSet employeeMatches = null;
		PreparedStatement preparedStatement = null; 

		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";
		
		String email = request.getParameter("employee_email");
		String password = request.getParameter("employee_password");
		

	    String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		Boolean valid = Login.verify(gRecaptchaResponse);
		
		try {
			initialContext = new InitialContext();
			environmentContext = (Context) initialContext.lookup("java:comp/env");
			dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
			
			session = request.getSession();
			if(email == null || "".equals(email))
			{
		        if(session.getAttribute("employee_email") != null)
		        {
					dispatcher = getServletContext().getRequestDispatcher(Global.dashboardServletPath);
					dispatcher.forward(request, response);
					return;
		        }
				dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/dashboard-login.jsp");
				dispatcher.forward(request, response);
				return;
			}
			else
			{
				if(session.getAttribute("employee_email") != null)
		        {
					dispatcher = getServletContext().getRequestDispatcher(Global.dashboardServletPath);
					dispatcher.forward(request, response);
		        }
				preparedStatement = conn.prepareStatement("select e.password from employees e where email = ?;");
				preparedStatement.setString(1, email);
				
				employeeMatches = preparedStatement.executeQuery();
				
				if(employeeMatches.next() && valid)
				{
					if( employeeMatches.getString(1).equals(password))
					{
				        session.setAttribute("employee_email",email);
						dispatcher = getServletContext().getRequestDispatcher(Global.dashboardServletPath);
						dispatcher.forward(request, response);

						return;
					}
					else
					{
						request.setAttribute("dashboard_error_message", "Invalid username or password");
						dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/dashboard-login.jsp");
						dispatcher.forward(request, response);
						return;
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
					return;
				}
			}
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		} finally {
			try{
				if(conn != null)
				{
					conn.close();
				}
				conn = null;
				if(employeeMatches != null)
				{
					employeeMatches.close();
				}
				employeeMatches = null;
				if(preparedStatement != null)
				{
					preparedStatement.close();
				}
				preparedStatement = null;
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
