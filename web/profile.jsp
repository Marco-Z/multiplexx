<%-- 
    Document   : index
    Created on : 19-mag-2015, 10.27.47
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#cc0000">
        <title>Profilo</title>
        <link rel="icon" type="image/png" href="img/favicon.ico">
        
        <script type="text/javascript" src="script/jquery-2.1.0.min.js"></script>
        
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script type="text/javascript" src="script/bootstrap.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="css/material.min.css">
        <script type="text/javascript" src="script/material.min.js"></script>
               
        <link href="css/styles.css" rel="stylesheet">
        <script type="text/javascript" src="script/script.js"></script>
    </head>
    
    <body>
        <%@include file="header.html" %>
        <div class="top">
            <%@include file="errori.jsp" %>
        </div>
        <div class="container nav-header jumbotron">


            <% 
                if (session.getAttribute("autenticated") == null){
            %>
            <div class="not-logged">
                <p>Non sei attualmente loggato al nostro sito...</p>
                <div class="margin-top">
                    <div class="col-lg-6">
                        <div class="well bs-component">
                            <form method="POST" action="register" class="form-horizontal">
                                <fieldset>
                                    <legend>Registrati:</legend>
                                    <div class="form-group">
                                        <label for="n" class="col-lg-2 control-label">Nome:    </label>
                                        <div class="col-lg-9">
                                            <input type="text" name="nome" class="form-control" id="n" placeholder="Nome">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="cn" class="col-lg-2 control-label">Cognome:    </label>
                                        <div class="col-lg-9">
                                            <input type="text" name="cognome" class="form-control" id="cn" placeholder="Cognome">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="e" class="col-lg-2 control-label">e-mail:    </label>
                                        <div class="col-lg-9">
                                            <input type="email" name="email" class="form-control" id="e" placeholder="e-mail">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="p" class="col-lg-2 control-label">Password:    </label>
                                        <div class="col-lg-9">
                                            <input type="password" name="password" class="form-control" id="p" placeholder="Password">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="p" class="col-lg-2 control-label">Reinserisci Password:    </label>
                                        <div class="col-lg-9">
                                            <input type="password" name="password2" class="form-control" id="p2" placeholder="Password">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-10 col-lg-offset-2">
                                            <button class="btn btn-default btn-danger" type="reset">Annulla</button>
                                            <button id="submit" type="submit" class="btn btn-primary">Registrati!</button>
                                        </div>
                                    </div>
                                    <p>${res}</p>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="well bs-component">
                            <form method="POST" action="login" class="form-horizontal">
                                <fieldset>
                                    <legend>Log-in:</legend>
                                    <div class="form-group">
                                        <label for="e" class="col-lg-2 control-label">e-mail    </label>
                                        <div class="col-lg-9">
                                            <input type="email" name="email" class="form-control" id="e" placeholder="e-mail">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="p" class="col-lg-2 control-label">Password    </label>
                                        <div class="col-lg-9">
                                            <input type="password" name="password" class="form-control" id="p" placeholder="Password">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-10 col-lg-offset-2">
                                            <button id="submit" type="submit" class="btn btn-primary">Log-in!</button>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                    <div class="col-lg-6">  
                        <div class="well bs-component">
                            <form method="POST" action="recupero" class="form-horizontal">
                                <fieldset>
                                    <legend>Recupero password:</legend>
                                    <div class="form-group">
                                        <label for="e" class="col-lg-2 control-label">e-mail    </label>
                                        <div class="col-lg-9">
                                            <input type="email" name="email" class="form-control" id="e" placeholder="e-mail">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-10 col-lg-offset-2">
                                            <button id="recupero" type="submit" class="btn btn-primary">Recupera password</button>
                                        </div>
                                    </div>                                    
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>    
            <% } else { %>
            <div class="row col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2 well">
                <h2>Sei loggato.</h2><br>
                <p>i tuoi dati sono:</p>
                <table class="table table-hover">
                    <tr>
                        <td>nome:</td>
                        <td><%= session.getAttribute("nome")%></td>
                    </tr>
                    <tr>
                        <td>cognome:</td>
                        <td><%= session.getAttribute("cognome")%></td>
                    </tr>
                    <tr>
                        <td>e-mail:</td>
                        <td><%= session.getAttribute("email")%></td>
                    </tr>
                    <tr>
                        <td>credito:</td>
                        <td>&euro; <%= session.getAttribute("credito")%></td>
                    </tr>
                    <tr>
                        <td>cronologia acquisti:</td>
                        <td>
                            <form action="cronologia.jsp" method="POST">
                                <input type="hidden" name="id_utente" value="<%= session.getAttribute("id_utente")%>">
                                <input type="submit" value="cronologia" class="btn btn-flat btn-primary btn-sm cron">
                            </form>
                        </td>
                    </tr>
                </table>
            </div>
          <%if(session.getAttribute("autenticated")!=null){  //se l'utente è autenticato guarda se è admin
            if(session.getAttribute("tipo").equals("ADMIN")){   //se è admin mostra le funzioni amministratore %> 
            <div class="row col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2 well text-center">
                <div class="panel panel-default box">
                    <form action="admin/incassi.jsp">
                        <input type="submit" value="Incassi" class="btn btn-raised btn-info">
                    </form>
                </div>
                <div class="panel panel-default box">
                    <form action="admin/clientiTop.jsp">
                        <input type="submit" value="Clienti Top" class="btn btn-raised btn-info">
                    </form>
                </div>
                <div class="panel panel-default box">
                    <form action="admin/cancellaPrenotazione.jsp">
                        <input type="e-mail" name="cancella" placeholder="e-mail" class="form-control text-center" id="inputDefault">
                        <input type="submit" value="Cancella Prenotazione" class="btn btn-raised btn-info">
                    </form>
                </div>
                <div class="panel panel-default box">
                    
                        <script>
                        function loadXMLDoc()
                        {
                        var xmlhttp;
                        if (window.XMLHttpRequest)
                          {// code for IE7+, Firefox, Chrome, Opera, Safari
                          xmlhttp=new XMLHttpRequest();
                          }
                        else
                          {// code for IE6, IE5
                          xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                          }
                        xmlhttp.onreadystatechange=function()
                          {
                          if (xmlhttp.readyState==4 && xmlhttp.status==200)
                            {
                            document.getElementById("risulta").innerHTML=xmlhttp.responseText;
                            }
                          }
                        xmlhttp.open("GET","admin/inserisci.jsp?X="+$('#inputDefault2').val().toString()+"&times="+$('#inputDefault2').val().toString(),true);
                       
                        xmlhttp.send();
                        }
                        </script>       
                    
                        <%--<form action="loadXMLDoc()"> --%>
                        <input type="number" name="X" placeholder="minuti" class="form-control text-center" id="inputDefault1">
                        <input type="number" name="times" placeholder="ripetizioni" class="form-control text-center" id="inputDefault2">
                        <button type="button" onclick="loadXMLDoc()" class="btn btn-info btn-raised">Genera spettacoli</button>
                        <br>
                        <div id="risulta"> Default: ogni 10 minuti e 10 ripetizioni</div>
                        <%--</form> --%>
                </div>
                <div class="panel panel-default box">
                    <form action="admin/resetta.jsp">
                        <input type="submit" value="! Cancella Programmazione !" class="btn btn-danger btn-raised">
                    </form>
                </div>
            </div>
              <%}}%>
            <div class="row col-lg-12">
                <form action="logout" method="GET" class="right">
                    <input type="submit" value="logout" class="btn btn-raised btn-danger">
                </form>
            </div>
            <% } %>


        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
