<%@include file="site-header.jsp" %>
<%@page import="fabflix.Global" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>fabflix - Login</title>
    </head>
    <body>
        <form method="post" action=<%=request.getContextPath() + Global.loginServletPath+""%>> <!-- login servlet handles authentication  -->
            <table class="center loginTable" border="1">
                <thead>
                    <tr>
                        <th colspan="2">fabflix Login</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Email/ User Name</td>
                        <td><input type="text" name="email" value="" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" value="" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                   
                </tbody>

            </table>
            
            <% 
                if (request.getAttribute("message") == null) {
                    //out.println("");
                } 
                else {
                    out.println("<div class='center'>"+request.getAttribute("message")+"</div>");
                }
            %>
            <div class="g-recaptcha" data-sitekey=<%=Global.SITE_KEY %>></div>
            
        </form>
        <script src='https://www.google.com/recaptcha/api.js'></script>
        
    </body>
</html>