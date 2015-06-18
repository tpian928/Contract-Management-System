window.onload = initHtml;

function initHtml () {
	var $$ = Dom7;
	$$('#submit').on('click', function () {
		var name = $$('#name').val();
		var desc = $$('#message').val();

		//遍历所有check
	    var funcStr = ""; 
        $$('input[type="checkbox"]:checked').each(
            function() {
                funcStr=funcStr+"-"+$$(this).prop('id');   
            }
        );

        if (cheackIt) {
    	    $$.post('addFunc', {name: name,desc:desc,funcStr:funcStr}, function (data) {
                
            });   
        };

	});
}

function cheackIt () {

}
