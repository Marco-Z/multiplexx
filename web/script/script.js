$(document).ready(function(){
    
    $("label").click(function(e){
        if( !e ) e = window.event;
        if(!$(e.target).hasClass("booked") && !$(e.target).hasClass("nd")){  //se il posto non è già prenotato o indisponibile
            $(e.target).toggleClass("free");
            $(e.target).toggleClass("selected");
        }
    });
    
    
    $('#hideshow').click(function(e) {
        $('.empty').toggle('show')
    });
    
});