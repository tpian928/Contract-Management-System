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

        if (isEmpty(name)==false&&isEmpty(desc)==false&&isEmpty(funcStr)==false) {
            o("checked");
    	    $$.post('addRole', {name: name,desc:desc,funcStr:funcStr,access_taken:localStorage.access_taken,id:localStorage.id}, function (data) {
                var obj=eval('('+data+')');
                var myApp = new Framework7();
                if (obj.result) {
                    myApp.alert('添加成功', '', function () {
                        window.location.herf="admin.html";
                    });
                }
                else{
                    myApp.alert('添加失败','');
                }

            });   
        }
        else{
            o("不正确");
        }

	});

    $$('#clear').on('click', function () {
        location.reload();
    });
    

}


