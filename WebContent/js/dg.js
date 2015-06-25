window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

    //禁用input
    $$('#cname').prop('disabled', true);
    $$('#customer').prop('disabled', true);
    $$('#input_01').prop('disabled', true);
    $$('#input_02').prop('disabled', true);
    $$('#pick').prop('disabled', true);

    $$('#dgBtn').on('click', function () {

        var message = $$('#message').val();
        
        if (isEmpty(cname)==false&&isEmpty(message)==false){
            $$.post('cAction', {message:message,action:'dg'}, function (data) {
                o(data);
                var obj=eval('('+data+')');
                var myApp = new Framework7();
                if (obj.result) {
                    myApp.alert('添加成功', '', function () {
                        o("cgggg");
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