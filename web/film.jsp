

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html" %>

<%@page import="java.util.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="dbAdministrator.sala"%>
<%@page import="dbAdministrator.spettacolo"%>
<%@page import="dbAdministrator.dbManager"%>
<% 
    dbManager  db = (dbManager)super.getServletContext().getAttribute("dbmanager");
%>
<% 
    int id = Integer.parseInt(request.getParameter("id"));
    spettacolo movie = db.getFilm(id);
    if(movie.getTitolo().equals("")){    // se il film non esiste reindirizzo a una pagina di errore
        response.sendRedirect("Error404");
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#cc0000">
        <title><%=movie.getTitolo()%></title>
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
        
        <%@include  file="header.html" %>
        <div>
            <%@include file="errori.jsp" %>
        </div>
        <div class="container-fluid main">
          <div class="row box">
            <div class="col-xs-12 col-sm-4 margin-top">
              <img src="<%=movie.getLocandina() %>" class="shadow img-responsive center-block cover">
            </div>
            <div class="col-sm-8">
              <div class="text-center">
                <h1 class="title"><%=movie.getTitolo()%></h1>
              </div>
              <div class="margin-top">
                <table class="table">
                  <tr>
                    <td><b>Genere:</b></td>
                    <td id="genere"><%=movie.getGenere()%></td>
                  </tr>
                  <tr>
                    <td><b>Durata:</b></td>
                    <td id="durata"><%=movie.getDurata()%></td>
                  </tr>
                  <tr>
                    <td><b>Regia:</b></td>
                    <td id="regia"><%=movie.getRegia()%></td>
                  </tr>
                  <tr>
                    <td><b>Nazionalità:</b></td>
                    <td id="naz"><%=movie.getNaz()%></td>
                  </tr>
                  <tr>
                    <td><b>Anno:</b></td>
                    <td id="anno"><%=movie.getAnno()%></td>
                  </tr>
                  <tr>
                    <td><b>Descrizione</b></td>
                    <td id="descrizione"><%=movie.getDescrizione()%></td>
                  </tr>
                </table>
              </div>
            </div>
          </div>

          <div class="row shadow-top padding-top info">
            <div class="col-xs-12 col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2">
                <table class="table table-hover">
                    <tr class="head">
                      <th>Data</th>
                      <th>Ora</th>
                      <th>Sala</th>
                      <th></th>
                    </tr>
                    <%  Iterator si = movie.sale.iterator();
                        Iterator idsi = movie.ids.iterator();
                        Iterator oi = movie.orari.iterator();
                        Date date= new Date();
                        Timestamp now = new Timestamp(date.getTime());
                        while(si.hasNext()){
                            sala s = (sala) si.next();
                            Integer id_spett = (Integer) idsi.next();
                            Timestamp tempo = (Timestamp) oi.next();
                            SimpleDateFormat sdfdata= new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat sdfora = new SimpleDateFormat("HH:mm");
                            String data = sdfdata.format(tempo);
                            String ora = sdfora.format(tempo);
                            String d = "";
                            %>
                            <tr>
                                <td><%=data%></td>
                                <td><%=ora%></td>
                                <td>Sala <%=s.getNome() %></td>
                                <td class="col-xs-1">
                                    <form method="GET" action="booking/scegliPosti.jsp">
                                        <%if(tempo.compareTo(now)<=0){
                                            d = "disabled";
                                        }%>
                                        <button type="submit" class="btn btn-raised red-button sm-btn <%=d%>" <%=d%>>prenota</button>
                                        <input name="id_s" type="hidden" value="<%=id_spett.intValue() %>"><br>
                                    </form>
                                </td>
                            </tr>
                            <%
                        }
                    %>
                </table>
                <div class="embed-responsive embed-responsive-16by9 shadow">
                    <iframe class="embed-responsive-item" src="<%=movie.getTrailer()%>?iv_load_policy=3" allowfullscreen ></iframe>
                </div>
            </div>
          </div>
        </div>
        <div class="padding-top"></div>
        
        <%@include  file="footer.jsp" %>

    </body>
</html>