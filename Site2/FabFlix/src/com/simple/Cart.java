package com.simple;


import java.io.Console;
import java.sql.*; // Enable SQL processing
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String username = Global.DB_USER;
	static String password = Global.DB_PASS;
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet movieList = null;
		
		HttpSession session = request.getSession(true);
		
		PrintWriter out = response.getWriter();
		
		
		if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
	        String redirectURL = Global.loginPath+"?message=";

	        RequestDispatcher dispatcher = null;
	        dispatcher = getServletContext().getRequestDispatcher(redirectURL);
	        dispatcher.forward(request, response);
	    }
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			conn = DriverManager.getConnection(dburl, username, password);
			
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
//			CartList temp= (CartList) request.getAttribute("cart");
			CartList temp= (CartList) session.getAttribute("cart");
			if(temp==null || temp.cart.size()==0)
			{
				out.println("Cart Empty... Generating Sample Cart.");
				session.setAttribute("cart",new CartList());
//				request.setAttribute("cart",new CartList());
				
			}
			
			
			temp=(CartList) session.getAttribute("cart");
			if(temp==null || temp.cart.size()==0)
			{
				out.println("Still Empty.");
			}
//			if(request.getParameter("cart") !=null)
//			{
			
			
//			for(CartItem a: temp.cart)
//			{
//				out.println(a.title +"---"+a.movieId);
//			}
			
//				rs = stmt.executeQuery("SELECT s.id, s.first_name, s.last_name, s.dob, s.photo_url" +
//						" FROM stars s" +
//						" WHERE s.first_name='" + names[0] + "' AND  s.last_name='" + names[1] + "';");
//				
//				ArrayList<String> movieTitles = new ArrayList<String>();
//				ArrayList<Integer> movieIDs = new ArrayList<Integer>();
//				while(movieList.next())
//				{
//					movieTitles.add(movieList.getString(1));
//					movieIDs.add((Integer)movieList.getInt(2));
//					
//				}
//				
////				
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Cart.jsp");
//
			dispatcher.forward(request, response);
//			}
//			else
//			{
//				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/empty-star-page.jsp");
//				dispatcher.forward(request, response);
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
