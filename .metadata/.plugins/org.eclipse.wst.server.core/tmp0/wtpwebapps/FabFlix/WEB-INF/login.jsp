<%@ page import ="java.sql.*" %>
<%@include file='var.jsp'%>

<%

    String email = request.getParameter("email");    
    String pwd = request.getParameter("password");
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", DB_USER, DB_PASS);
    // Statement st = con.createStatement();
    // String query="select * from customers where email='" + email + "' and password= '" + pwd + "'";
    // ResultSet rs = st.executeQuery(query);

    String selectSQL = "select * from customers where email='" + email + "' and password='" + pwd + "'";
    PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
    // // preparedStatement.setInt(1, 1001);
    ResultSet rs = preparedStatement.executeQuery(selectSQL );
    // out.println(query);

    if (rs.next()) 
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
        session.setAttribute("email",email);
        session.setAttribute("id", rs.getInt(1));
        session.setAttribute("first_name", rs.getString(2));
        session.setAttribute("last_name", rs.getString(3));
        session.setAttribute("cc_id", rs.getString(4));
        session.setAttribute("address", rs.getString(5));
        //out.println("welcome " + email);
        //out.println("<a href='logout.jsp'>Log out</a>");
        response.sendRedirect(homePath);
    } 
    else 
    {
        String redirectURL = loginPath + "?message=Invalid password or User Name. Try Again.";
        response.sendRedirect(redirectURL);
    }

%>