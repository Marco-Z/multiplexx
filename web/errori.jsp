<% if (session.getAttribute("errore")!="" && session.getAttribute("errore")!=null) { %>
<div class="over alert">
    <div id="errors" class="alert-dismissable alert-danger col-md-4 
                            col-md-offset-4 col-sm-6 col-sm-offset-3
                            col-xs-8 col-xs-offset-2 shadow">
        <button type="button" class="close" data-dismiss="alert">×</button>
        <p><strong><%=session.getAttribute("errore") %></strong></p>
        <%session.setAttribute("errore", "");%>
    </div>
</div>
<% } %>

<% if (session.getAttribute("messaggio")!="" && session.getAttribute("messaggio")!=null) { %>
<div class="over alert">
    <div id="errors" class="alert-dismissable alert-info col-md-4 
                            col-md-offset-4 col-sm-6 col-sm-offset-3
                            col-xs-8 col-xs-offset-2 shadow">
        <button type="button" class="close" data-dismiss="alert">×</button>
        <p><strong><%=session.getAttribute("messaggio") %></strong></p>
        <%session.setAttribute("messaggio", "");%>
    </div>
</div>
<% } %>