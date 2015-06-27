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
				var obj=eval('('+data+')');
                var myApp = new Framework7();
                if (obj.result) {
                    myApp.alert('添加成功', '', function () {
                        location.herf="/CM/showuser";
                    });
                }
                else{
                    myApp.alert('添加失败','');
                }
			}); 
		}
		else{
			a("没勾选");
		}
	});	

}

