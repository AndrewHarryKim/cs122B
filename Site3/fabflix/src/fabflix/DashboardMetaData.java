package fabflix;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


@WebServlet("/DashboardMetaData")
public class DashboardMetaData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static DatabaseMetaData metadata = null;

    public DashboardMetaData() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("employee_email") == null) || ("".equals(session.getAttribute("employee_email")))) {
			String redirectURL = request.getContextPath() + Global.dashboardLoginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
	    PrintWriter out = response.getWriter();
		Connection conn = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ArrayList<String> tables = null;

		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";

		try {
			initialContext = new InitialContext();
			environmentContext = (Context) initialContext.lookup("java:comp/env");
			dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
		
		
			metadata = conn.getMetaData();
		    String table[] = { "TABLE" };
			// receive the Type of the object in a String array.
			
			rs = metadata.getTables(null, null, null, table);
			
			tables = new ArrayList<String>();
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}
		    
			// Print the columns properties of the actual table
			for (String actualTable : tables) {
				rs2 = metadata.getColumns(null, null, actualTable, null);
				out.write("<div>" + actualTable.toUpperCase() + "</div>");
				while (rs2.next()) {
					out.write("<div>" + rs2.getString("COLUMN_NAME") + " " + rs2.getString("TYPE_NAME") + " "
							+ rs2.getString("COLUMN_SIZE") + "</div>");
				}
				out.write("<br/><br/><br/>");
			}
	    } catch (SQLException | NamingException e) {
			e.printStackTrace();
		}finally {
			try{
				if(conn != null)
				{
					conn.close();
				}
				conn = null;
				if(rs != null)
				{
					rs.close();
				}
				rs = null;
				if(rs2 != null)
				{
					rs2.close();
				}
				rs2 = null;
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
