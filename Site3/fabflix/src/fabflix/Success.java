package fabflix;

import java.sql.*; // Enable SQL processing
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Success")
public class Success extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public Success() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("email") == null) || ("".equals(session.getAttribute("email")))) {
			String redirectURL = request.getContextPath() + Global.loginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/success.jsp");
			dispatcher.forward(request, response);

		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
