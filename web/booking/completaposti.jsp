<%-- 
    Document   : bookedseats
    Created on : 4-ago-2015, 10.29.50
    Author     : Lele
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="dbAdministrator.sala"%>
<%@page import="dbAdministrator.dbManager"%>
<%@page import="java.util.Random" %>

<% 
            int id_utente =-1;
        if(session.getAttribute("autenticated")==null){
            String redirectURL = "../profile.jsp";
            response.sendRedirect(redirectURL);
        } else {
             id_utente = (int) session.getAttribute("id_utente");


        }
          %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#cc0000">
        <title>Risultato operazione:</title>
        <link rel="icon" type="image/png" href="../img/favicon.ico">
        
        <script type="text/javascript" src="../script/jquery-2.1.0.min.js"></script>
        
        <link href="../css/bootstrap.min.css" rel="stylesheet">
        <script type="text/javascript" src="../script/bootstrap.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="../css/material.min.css">
        <script type="text/javascript" src="../script/material.min.js"></script>
                
        <link href="../css/styles.css" rel="stylesheet">
        <script type="text/javascript" src="../script/script.js"></script>
    </head>
    
    <body>
        
        <%@include file="../header-in.html" %>
        <div class="top">
            <%@include file="../errori.jsp" %>
        </div>
        <div class="container nav-header jumbotron text-center">
        

        <% 
            dbManager  db = (dbManager)super.getServletContext().getAttribute("dbmanager");

            ArrayList<String> posti = new ArrayList<>();
            ArrayList<String> tipo = new ArrayList<>();
            Map m=request.getParameterMap();
                Set s = m.entrySet();
                Iterator it = s.iterator();

                    while(it.hasNext()){

                        Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

                        String key             = entry.getKey();
                        String[] value         = entry.getValue();
                        if (key.equalsIgnoreCase("spetid")) { //fai niente

                        }
                        else {
                            posti.add(key);
                            tipo.add(value[0]);

                                }
                    }
                    //debug
                    if(id_utente!=-1){
                       
                        String res=db.prenota(id_utente, Integer.valueOf(request.getParameter("spetid")), posti, tipo);
                            if(res.equals("")){
                                out.println("L'operazione è stata eseguita correttamente\n"
                                        +"Le ricordiamo che il biglietto elettronico è le è stato spedito via e-mail\n"
                                        + "Può gestire questa e le altre prenotazioni dal menù del suo profilo");
                            }
                            else {
                                out.print("Prenotazione non effettuata, motivo: \n"+res);
                            }
                    }    

        %>

        </div>
<%@include file="../footer.jsp" %>        
    </body>
</html>
