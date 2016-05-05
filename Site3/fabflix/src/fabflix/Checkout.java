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


@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";

    public Checkout() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
			String redirectURL = request.getContextPath() + Global.loginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			
			conn = DriverManager.getConnection(dburl, Global.DB_USER, Global.DB_PASS);
			
			stmt = conn.createStatement();
			CartList temp= (CartList) session.getAttribute("cart");
			if(temp==null || temp.cart.size()==0)
			{
				out.println("Cart Empty... Generating Sample Cart.");
				session.setAttribute("cart",new CartList());
				
			}
			else
			{
				out.println("Cart Found.");
			}
			
			temp=(CartList) session.getAttribute("cart");
			if(temp.cart.size()==-1)
			{
				out.println("Still Empty.");
			}
			else
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/checkout.jsp");
				dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
				{
					rs.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
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
