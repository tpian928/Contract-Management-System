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

        if (isEmpty(name)==false&&isEmpty(desc)==false&&isEmpty(funcStr)==false&&isEmpty(localStorage.access_taken)==false) {
            o("checked");
    	    $$.post('addFunc', {name: name,desc:desc,funcStr:funcStr,access_taken:localStorage.access_taken,id:localStorage.id}, function (data) {
                o(data);
            });   
        }
        else{
            o("不正确");
        }

	});
}

function cheackIt () {
    
}
