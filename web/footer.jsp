<div id="footer">
    <% if(session.getAttribute("tipo")!=null && session.getAttribute("tipo").equals("ADMIN")){ %>
        <div>
            sei un cazzo di admin
        </div>
    <% } else{%>
        <div>
            Progetto realizzato per il corso di introduzione alla programmazione per il web dell'università di trento.<br>
            Realizzato da Lorenzo Turi, Germana Baldi, Filippo Manara, Daniele Gubert, Marco Zugliani
        </div>
    <% }%>
</div>