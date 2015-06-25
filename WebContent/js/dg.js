window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

    //禁用input
    $$('#cname').prop('disabled', true);
    $$('#customer').prop('disabled', true);
    $$('#input_01').prop('disabled', true);
    $$('#input_02').prop('disabled', true);
    $$('#pick').prop('disabled', true);

    $$('#draft').on('click', function () {
        var bdate = $input1.pickadate( 'picker' ).get('select', 'yyyy-mm-dd');
        var edate = $input2.pickadate( 'picker' ).get('select', 'yyyy-mm-dd');
        
        var cname = $$('#cname').val();
        var customer = $$('#customer').val();
        var message = $$('#message').val();
        
        if (isEmpty(cname)==false&&isEmpty(message)==false){
            $$.post('cAction', {cname:cname,customer:customer,message:message,bdate:bdate,edate:edate,action:'draft'}, function (data) {
                o(data);
                var obj=eval('('+data+')');
                var myApp = new Framework7();
                if (obj.result) {
                    myApp.alert('添加成功', '', function () {
                        location.herf="/CM/home";
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