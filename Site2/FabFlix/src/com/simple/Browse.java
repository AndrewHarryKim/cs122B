package com.simple;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Browse
 */
@WebServlet("/Browse")
public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Browse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		RequestDispatcher dispatcher = null;
		if (request.getParameter("by") == null)
		{
			dispatcher = request.getServletContext().getRequestDispatcher("/Main");
			dispatcher.forward(request, response);
		}
		else if (request.getParameter("by").equals("genre"))
		{ 
			dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/browse.jsp?by=genre");
			dispatcher.forward(request, response);
		}
		else if (request.getParameter("by").equals("title"))
		{ 
			dispatcher =request.getServletContext().getRequestDispatcher("/WEB-INF/browse.jsp?by=title");
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
