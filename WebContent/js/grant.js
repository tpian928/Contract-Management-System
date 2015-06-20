window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

	$$('#submit').on('click', function () {
		o("submit")
		submit();
	});	

}

function submit () {
	
	var $$ = Dom7;

	var roleStr = ""; 
	$$('input[type="checkbox"]:checked').each(
	    function() {
	        roleStr=roleStr+"-"+$$(this).prop('id');   
	    }
	);

	if (isEmpty(roleStr)==false){
				
	}


}