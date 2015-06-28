window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;
    var myApp = new Framework7();
    
    $$('#draft').on('click', function () {

        
        var name = $$('#name').val();
        var phone = $$('#phone').val();
        var address = $$('#address').val();
        var fax = $$('#fax').val();
        var email = $$('#email').val();
        var bank = $$('#bank').val();
        var account = $$('#account').val();
        var more = $$('#more').val();
        
        if (isEmpty(name)==false&&isEmpty(phone)==false&&isEmpty(address)==false&&isEmpty(fax)==false&&isEmpty(email)==false&&isEmpty(bank)==false&&isEmpty(account)==false&&isEmpty(more)==false){
            $$.post('cAction', {name:name,phone:phone,address:address,fax:fax,email:email,bank:bank,account:account,more:more,action:'addc'}, function (data) {
                o(data);
                var obj=eval('('+data+')');
                
                if (obj.result) {
                    myApp.alert('添加成功', '', function () {
                    	o("thissss");
                        window.location.href="/CM/index";
                    });
                }
                else{
                    myApp.alert('添加失败','');
                }
            }); 
        }
        else{
            myApp.alert('您未完全填写','');
        }

    });

    $$('#clear').on('click', function () {
        location.reload();
    });

}

