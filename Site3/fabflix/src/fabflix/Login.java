package fabflix;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import fabflix.Global;
import java.sql.*;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Login() {
        super();
    }

    public static boolean verify( String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }
        try {
            URL verifyUrl = new URL(Global.SITE_VERIFY_URL);

            // Open Connection to URL
            HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();


            // Add Request Header
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


            // Data will be sent to the server.
            String postParams = "secret=" + Global.SECRET_KEY + "&response=" + gRecaptchaResponse;

            // Send Request
            conn.setDoOutput(true);

            // Get the output stream of Connection
            // Write data in this stream, which means to send data to Server.
            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());

            outStream.flush();
            outStream.close();

            // Response code return from server.
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode=" + responseCode);


            // Get the InputStream from Connection to read data sent from the server.
            InputStream is = conn.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            // ==> {"success": true}
            System.out.println("Response: " + jsonObject);

            boolean success = jsonObject.getBoolean("success");
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		
        RequestDispatcher dispatcher = null;
        Connection conn = null;
        ResultSet rs = null;

		Context initialContext = null;
        Context environmentContext = null;
        DataSource dataSource = null;
        String dataResourceName = "jdbc/moviedb";

		String email = request.getParameter("email");    
	    String pwd = request.getParameter("password");
	    String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
        // Verify CAPTCHA.
        boolean valid = verify(gRecaptchaResponse);
	    try {
	    	if(session.getAttribute("email") != null && !("".equals(session.getAttribute("email")))  )
	    	{
		        String redirectURL = getServletContext().getContextPath() + Global.homeServletPath;
		        response.sendRedirect(redirectURL);
	    	}
	    	else if(  (email != null && !("".equals(email))) &&
	    			(pwd != null && !("".equals(pwd))))
	    	{
				initialContext = new InitialContext();
				environmentContext = (Context) initialContext.lookup("java:comp/env");
				dataSource = (DataSource) environmentContext.lookup(dataResourceName);
				conn = dataSource.getConnection();
			    
		
			    String selectSQL = "";
			    PreparedStatement preparedStatement = conn.prepareStatement("select * from customers where email = ? and password = ?;");
			    preparedStatement.setString(1, email );
			    preparedStatement.setString(2, pwd );

			    rs = preparedStatement.executeQuery();
			    if (rs.next()&& valid) 
			    {
			        
			        //(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
			        //User x= new User(rs);
			        /*
			        id:integer (primary key)
					first_name:varchar(50) 
					last_name:varchar(50) 
					cc_id:varchar(20), referencing creditcards.id
					address:varchar(200) 
					email:varchar(50) 
					password:varchar(20) 
			        */
			        CartList cart = new CartList();
			        session.setAttribute("email",email);
			        session.setAttribute("id", rs.getInt(1));
			        session.setAttribute("first_name", rs.getString(2));
			        session.setAttribute("last_name", rs.getString(3));
			        session.setAttribute("cc_id", rs.getString(4));
			        session.setAttribute("address", rs.getString(5));
			        session.setAttribute("cart", cart);
			        //out.println("welcome " + email);
			        //out.println("<a href='logout.jsp'>Log out</a>");
			        String redirectURL = getServletContext().getContextPath() + Global.homeServletPath;
			        response.sendRedirect(redirectURL);
			    } 
			    else 
			    {
		    		if(!valid) request.setAttribute("message","Invalid Recaptcha, You Must Be A Robot");
		    		else request.setAttribute("message","Invalid password or User Name. Try Again.");
			        String forwardURL = Global.loginJSPPath;
					dispatcher = getServletContext().getRequestDispatcher(forwardURL);
			        dispatcher.forward(request, response);
			    }
	    	}
	    	else
	    	{
				dispatcher = getServletContext().getRequestDispatcher(Global.loginJSPPath);
		        dispatcher.forward(request, response);
	    	}
	    } catch ( SQLException | NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
				{
						rs.close();
				}
				rs = null;
				if(conn != null)
				{
					conn.close();
				}
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
