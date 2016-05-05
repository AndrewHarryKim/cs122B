package fabflix;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/DashboardMetaData")
public class DashboardMetaData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static DatabaseMetaData metadata = null;
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";

    public DashboardMetaData() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
	    /*if ((session.getAttribute("employee_email") == null) || ("".equals(session.getAttribute("employee_email")))) {
			String redirectURL = request.getContextPath() + Global.dashboardLoginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }*/
	    PrintWriter out = response.getWriter();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(dburl, Global.DB_USER, Global.DB_PASS);
		
		
			metadata = conn.getMetaData();
		    String table[] = { "TABLE" };
			ResultSet rs = null;
			ArrayList<String> tables = null;
			// receive the Type of the object in a String array.
			
			rs = metadata.getTables(null, null, null, table);
			
			tables = new ArrayList<String>();
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}
		    
			ResultSet rs2 = null;
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
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
