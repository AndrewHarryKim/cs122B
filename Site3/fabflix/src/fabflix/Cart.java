package fabflix;


import java.sql.*; // Enable SQL processing

import java.io.IOException;
import java.io.PrintWriter;

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


@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";
       
 
    public Cart() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("email") == null) || ("".equals(session.getAttribute("email")))) {
			String redirectURL = request.getContextPath() + Global.loginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
	    
	    
	    
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";
        
		PrintWriter out = response.getWriter();
		
		if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
	        String redirectURL = Global.loginServletPath+"?message=";

	        RequestDispatcher dispatcher = null;
	        dispatcher = getServletContext().getRequestDispatcher(redirectURL);
	        dispatcher.forward(request, response);
	    }
		
		try {

			initialContext = new InitialContext();
			environmentContext = (Context) initialContext.lookup("java:comp/env");
			dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
			
			stmt = conn.createStatement();
			CartList temp = (CartList) session.getAttribute("cart");
			if(temp == null )
			{
				out.println("Cart Empty... Generating Sample Cart.");
				session.setAttribute("cart",new CartList());
				
			}

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/cart.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
				{
					rs.close();
				}
				rs = null;
				if(stmt != null)
				{
					stmt.close();
				}
				stmt = null;
				if(conn != null)
				{
					conn.close();
				}
				conn = null;
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

