<%-- 
    Document   : displayUserProfile
    Created on : 10 Mar 2023, 00:38:27
    Author     : Thato Keith Kujwane
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Profile</title>
    </head>
    <body>
        <%
            String firstName = (String) session.getAttribute("firstName");
            String middleName = (String) session.getAttribute("middleName");
            String surname = (String) session.getAttribute("surname");
        %>
        
        <h1>Profile Deletion Page</h1>
        
        
    </body>
</html>
