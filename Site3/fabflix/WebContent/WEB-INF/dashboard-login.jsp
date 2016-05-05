<%@include file="site-header.jsp" %>
<%@page import="fabflix.Global" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<link rel="stylesheet" type="text/css" href="css/styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>fabflix employee - Login</title>
    </head>
    <body class="employee-body">
        <form method="post" action=<%=request.getContextPath() + Global.dashboardServletPath %>>
            <table class="center loginTable" border="1">
                <thead>
                    <tr>
                        <th colspan="2">fabflix employee Login</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Email</td>
                        <td><input type="text" name="employee_email" value="" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="employee_password" value="" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                   
                </tbody>

            </table>
            
            <% 
                if (request.getAttribute("dashboard_error_message") == null) {
                    //out.println("");
                } 
                else {
                    out.println("<div class='center'>"+request.getAttribute("dashboard_error_message")+"</div>");
                }
            %>
            <div class="g-recaptcha" data-sitekey=<%=Global.SITE_KEY %>></div>
            
        </form>
        <script src='https://www.google.com/recaptcha/api.js'></script>
        
    </body>
</html>