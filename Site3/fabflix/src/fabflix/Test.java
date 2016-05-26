package fabflix;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Test() {
        super();
    }

    public String welcome(HttpServletRequest request) throws NamingException, SQLException {
    	/**
         * Get initial context that has references to all configurations and
         * resources defined for this web application.
         */
        Context initialContext = new InitialContext();
 
        /**
        * Get Context object for all environment naming (JNDI), such as
         * Resources configured for this web application.
         */
        Context environmentContext = (Context) initialContext
                .lookup("java:comp/env");
        /**
         * Name of the Resource we want to access.
         */
        String dataResourceName = "jdbc/moviedb";
        /**
         * Get the data source for the MySQL to request a connection.
         */
        DataSource dataSource = (DataSource) environmentContext.lookup(dataResourceName);
        /**
         * Request a Connection from the pool of connection threads.
         */
        Connection conn = dataSource.getConnection();
        StringBuilder msg = new StringBuilder();
        /**
         * Use Connection to query the database for a simple table listing.
         * Statement will be closed automatically.
         */
        try (Statement stm = conn.createStatement()) {
            String query = "show tables;";
            ResultSet rs = stm.executeQuery(query);
            // Store and return result of the query
            while (rs.next()) {
                msg.append(rs.getString("Tables_in_moviedb"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            // Release connection back to the pool
            if (conn != null) {
                conn.close();
            }
            conn = null; // prevent any future access
        }
 
 
        return "Maven Java Web Application Project: Success! The show tables result is: "
                + msg.toString();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.getWriter().append(welcome(request));
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
