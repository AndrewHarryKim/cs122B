package fabflix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Dashboard() {
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
		
		
		
		dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
