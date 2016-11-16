<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html" %>

<% 
    if (session.getAttribute("autenticated") == null){ //non loggato
    String redirectURL = "../profile.jsp";
    response.sendRedirect(redirectURL);
    }
    if(session.getAttribute("autenticated")!=null){
        if(!session.getAttribute("tipo").equals("ADMIN")){ //non amministratore
            String redirectURL = "../profile.jsp";
            response.sendRedirect(redirectURL);
        }
    }
%>

<%@page import="dbAdministrator.dbManager"%>
<% 
    dbManager  db = (dbManager)super.getServletContext().getAttribute("dbmanager");
    int x = 10;
    if(request.getParameter("X")!=""){
        x = Integer.parseInt(request.getParameter("X"));
    }
    int times = 10;
    if(request.getParameter("times")!=""){
        times = Integer.parseInt(request.getParameter("times"));
    }
    ArrayList<String> titolo = db.insertFilm(x, times);
    out.print("i film messi in programmazione sono: <br>");
    for(String s : titolo){
        out.print(s +"<br>");
    }
    response.setHeader("Refresh", "4;url=../profile.jsp");
%>