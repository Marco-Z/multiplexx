<%-- 
    Document   : bookedseats
    Created on : 4-ago-2015, 10.29.50
    Author     : Lele
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html" %>

<%@page import="java.sql.Timestamp"%>
<%@page import="dbAdministrator.sala"%>
<%@page import="dbAdministrator.dbManager"%>
<%@page import="java.util.Random" %>
<% 
    dbManager  db = (dbManager)super.getServletContext().getAttribute("dbmanager");
%>
<% 
    sala s = new sala( 13, 13 , "ciao", "ciao" );
    Random rn = new Random();
    s.setPosto(rn.nextInt(13), 5, 'o');
    s.setPosto(4, rn.nextInt(13), 'o');
    s.setPosto(rn.nextInt(13), rn.nextInt(13), 'o');
    s.setPosto(3, rn.nextInt(13), 'o');
    s.setPosto(rn.nextInt(13), 1, 'o');
    
    for(int lu=1;lu<s.getLunghezza();lu++){
        for(int la=1;la<s.getLarghezza();la++){
            
        
        %>
        <div id= <%
            if( s.getPosto(lu,la) == 'o' ) {             
             %> "rcorners2"> <%= la %> </div>
        
            <% } else { %>               
            "rcorners1">    <%= la %>   </div>                           
<%
    }} 
%> 
<br>
<%   } %>