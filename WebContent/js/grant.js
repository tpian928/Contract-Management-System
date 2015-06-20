window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

	$$('#grantBtn').on('click', function () {
		o("there")
		var roleStr = ""; 
		var grantid = localStorage.grantid
		$$('input[type="checkbox"]:checked').each(
		    function() {
		        roleStr=roleStr+"-"+$$(this).prop('id');   
		    }
		);

		if (isEmpty(roleStr)==false){
			$$.post('grant', {roleStr:roleStr,grantid:grantid}, function (data) {
				x(data);
			}); 
		}
		else{
			a("没勾选");
		}
	});	

}

