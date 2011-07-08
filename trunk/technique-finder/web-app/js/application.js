$(function() {
    $("#spinner").ajaxSend(function(event, request, settings){
        $(this).fadeIn();
    }).ajaxComplete(function(event, request, settings){
        $(this).fadeOut();
    });
    
    $.ajaxSetup({
    	timeout: 60000, // global AJAX requests timeout (60 seconds)
    	error:function(request, textStatus, errorThrown){
			if(textStatus=='timeout'){
				humanMsg.displayMsg('Request Time out.');
			}else {
				humanMsg.displayMsg('Server error happened while processing your request');
			}
		}
	});
});
