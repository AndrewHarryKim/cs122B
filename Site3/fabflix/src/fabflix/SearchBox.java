package fabflix;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.sun.rowset.CachedRowSetImpl;

@WebServlet("/SearchBox")
public class SearchBox extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int STRING_ARG_TYPE = 0;
	private static final int INT_ARG_TYPE = 1;
       
	
	
    public SearchBox() {
        super();

    }

    private String getTitleSearchQueryString(HttpServletRequest request, ArrayList<String> argumentList, ArrayList<Integer> argumentTypes) {
    	
    	
		String queryHelper = request.getParameter("key");
		String queryString = "";
		
		
		queryString += " (m.title LIKE ?";
		argumentList.add("%" + queryHelper + "%");
		argumentTypes.add(STRING_ARG_TYPE);
		
		for(int i = 0; i < queryHelper.length(); i++)
		{
			queryString += " OR m.title LIKE ?";
			argumentList.add("%" + queryHelper.substring(0,i) + "_" + queryHelper.substring(i) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
		}
		
		queryString += " OR m.title LIKE ?";
		argumentList.add("%" + queryHelper.substring(0,queryHelper.length()-1) + "%");
		argumentTypes.add(STRING_ARG_TYPE);
		
		for(int i = 1; i < queryHelper.length(); i++)
		{
			queryString += " OR m.title LIKE ?";
			argumentList.add("%" + queryHelper.substring(0,i-1) + queryHelper.substring(i) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
		}
		
		queryString += " OR m.title LIKE ?";
		argumentList.add("%" + queryHelper.substring(1) + "%");
		argumentTypes.add(STRING_ARG_TYPE);
		
		queryString += ")";
		
		return queryString;
	}
    private void fillPreparedStatement(PreparedStatement prepStmt, ArrayList<String> argumentList, ArrayList<Integer> argumentTypes)
    {
		try {
	    	for(int i = 0;  i < argumentList.size(); i++)
			{
				if(argumentTypes.get(i).equals(STRING_ARG_TYPE))
				{
						prepStmt.setString(i+1, argumentList.get(i));
					
				} else
				{
					try{
						prepStmt.setInt(i+1, Integer.parseInt(argumentList.get(i)));
					} catch (NumberFormatException e) {
						prepStmt.setString(i+1, argumentList.get(i));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";
        
		ArrayList<String> argumentList = new ArrayList<String>();
		ArrayList<Integer> argumentTypes = new ArrayList<Integer>();
		
		try {

			
			
			
			/*************************   Establish SQL Connection  *****************************/
			initialContext = new InitialContext();
			environmentContext = (Context) initialContext.lookup("java:comp/env");
			dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
			
			String key = request.getParameter("key");
			String queryString = "";
			PrintWriter out = response.getWriter();
			
			
			if(key != null && !("".equals(key)))
			{
				queryString += "SELECT m.title, m.id FROM movies m WHERE ";
				queryString += getTitleSearchQueryString(request, argumentList, argumentTypes);
				prepStmt = conn.prepareStatement(queryString + " LIMIT 15;");
				fillPreparedStatement(prepStmt, argumentList, argumentTypes);
				rs = prepStmt.executeQuery();
				StringBuilder htmlString = new StringBuilder();
				htmlString.append("<table id=\"autoComplete\" class=\"autoComplete\">");
				htmlString.append("<colgroup><col style=\"width:100%\"></colgroup>");
				htmlString.append("<tbody>");
				while(rs.next())
				{
					htmlString.append("<tr><td><div><a href='MoviePage?movieid=" + rs.getInt(2) + "'>" + rs.getString(1) + "</a></div></td></tr>");
				}

				htmlString.append("</tbody>");
				htmlString.append("</table>");
				out.append(htmlString.toString());
			}
			
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
				{
					rs.close();
				}
				rs = null;
				if(prepStmt != null)
				{
					prepStmt.close();
				}
				prepStmt = null;
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
