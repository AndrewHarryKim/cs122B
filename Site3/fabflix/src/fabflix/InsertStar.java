package fabflix;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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

@WebServlet("/InsertStar")
public class InsertStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertStar() {
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
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";
		
		

		try {
			initialContext = new InitialContext();
			environmentContext = (Context) initialContext.lookup("java:comp/env");
			dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
		
		
			String starname = request.getParameter("insert-star-name");
			String dob = request.getParameter("insert-star-dob");
			String photo = request.getParameter("insert-star-photo-url");
			
			
			if(starname != null && !("".equals(starname)))
			{
				String[] splt = starname.split(" ");
	
				String fn = "";
				String ln = "";
				StringBuilder insertionString = new StringBuilder("INSERT INTO stars (first_name, last_name");
				StringBuilder valueString = new StringBuilder(" values(");
				ArrayList<String> arguments = new ArrayList<String>();
				
				if (splt.length == 0)
				{
					request.setAttribute("insert_star_message", "This name is empty...");
					dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/insert-star.jsp");
					dispatcher.forward(request, response);
				}
				else if (splt.length == 1) {
					ln = splt[0];
					valueString.append(" '', ?");
					arguments.add(ln);
					request.setAttribute("insert_star_message", "Defaulting to... \nEmpty First Name\n<Last Name>: " + ln);
				} else if (splt.length == 2) {
					fn = splt[0];
					ln = splt[1];
					valueString.append(" ?, ?");
					arguments.add(fn);
					arguments.add(ln);
				} else {
	
					fn = splt[0];
					ln = splt[1];
					for (int i = 2; i < splt.length; ++i)
						ln += " " + splt[i];
	
					valueString.append(" ?, ?");
					arguments.add(fn);
					arguments.add(ln);
					request.setAttribute("insert_star_message", "Defaulting to... \n<First Name>: " + fn + "\n<Last Name>: " + ln);
				}
				
				if(dob != null && !("".equals(dob)))
				{
					
					java.util.Date theDate = null;
					try {
						theDate = new SimpleDateFormat("ddMMyyyy").parse(dob.replaceAll("/", ""));
					
						java.sql.Date sqlDate = new java.sql.Date(theDate.getTime());
						String date = sqlDate.toString();
		
						insertionString.append(", dob");
						valueString.append(", ?");
						arguments.add(date);
					} catch (ParseException e) {
						request.setAttribute("insert_star_message", "Invalid dob format");
						dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/insert-star.jsp");
						dispatcher.forward(request, response);
					}
				}
				if(photo != null && !("".equals(photo)))
				{
	
					insertionString.append(", photo_url");
					valueString.append(", ?");
					arguments.add(photo);
				}
				insertionString.append(") ");
				valueString.append(");");
				insertionString.append(valueString.toString());
				
				try {
					preparedStatement = conn.prepareStatement(insertionString.toString());
					for(int i = 0;  i < arguments.size(); i++)
					{
						preparedStatement.setString(i+1, arguments.get(i));
					}
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	
				request.setAttribute("insert_star_message", "Star successfully added to database");
			}
		} catch (SQLException | NamingException e1) {
			e1.printStackTrace();
		}
		dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/insert-star.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
