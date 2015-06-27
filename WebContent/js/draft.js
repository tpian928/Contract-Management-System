window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;
    var myApp = new Framework7();
    
    var $input1 = $( '#input_01' ).pickadate({
        formatSubmit: 'yyyy/mm/dd',
        container: '#container',
        closeOnSelect: false,
        closeOnClear: false,
    })
    var $input2 = $( '#input_02' ).pickadate({
        formatSubmit: 'yyyy/mm/dd',
        container: '#container',
        closeOnSelect: false,
        closeOnClear: false,
    })

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

