package fabflix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Logout() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		session.setAttribute("id", null);
		session.setAttribute("first_name", null);
		session.setAttribute("last_name", null);
		session.setAttribute("cc_id", null);
		session.setAttribute("address", null);
		session.setAttribute("email", null);
		session.setAttribute("cart", null);
		session.invalidate();

		String redirectURL = request.getContextPath() + Global.loginServletPath;
		response.sendRedirect(redirectURL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}