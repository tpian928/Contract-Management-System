window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

	$$(document).on('click','#grantBtn', function () {
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
				var obj=eval('('+data+')');
                var myApp = new Framework7();
                if (obj.result) {
                    myApp.alert('授权成功', '', function () {
                        window.location.href="/CM/showuser";
                    });
                }
                else{
                    myApp.alert('授权失败','');
                }
			}); 
		}
		else{
			a("没勾选");
		}
	});	

}

