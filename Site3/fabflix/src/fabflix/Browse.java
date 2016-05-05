package fabflix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fabflix.Global;;

@WebServlet("/Browse")
public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Browse() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("email") == null) || ("".equals(session.getAttribute("email")))) {
			String redirectURL = request.getContextPath() + Global.loginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
		RequestDispatcher dispatcher = null;
		
		
		if (request.getParameter("by") == null)
		{
			String redirectURL = request.getContextPath() + Global.homeServletPath;
			response.sendRedirect(redirectURL);
		}
		else if (request.getParameter("by").equals("genre"))
		{ 
			dispatcher = request.getServletContext().getRequestDispatcher(Global.browseGenreJSP);
			dispatcher.forward(request, response);
		}
		else if (request.getParameter("by").equals("title"))
		{ 
			dispatcher = request.getServletContext().getRequestDispatcher(Global.browseTitleJSP);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
