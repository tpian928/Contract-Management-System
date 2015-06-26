window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;
    var myApp = new Framework7();

    //禁用input
    $$('#cname').prop('disabled', true);
    $$('#customer').prop('disabled', true);

    $$('#qdBtn').on('click', function () {

        var message = $$('#message').val();
        
        if (isEmpty(cname)==false&&isEmpty(message)==false){
            $$.post('cAction', {message:message,action:'qdaction'}, function (data) {
                o(data);
                var obj=eval('('+data+')');
                
                if (obj.result) {
                    myApp.alert('添加成功', '', function () {
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

}