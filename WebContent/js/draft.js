window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

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
        var bdate = $input1.pickadate( 'picker' );
        var edate = $input2.pickadate( 'picker' );
        o("bdate is "+bdate)
        var cname = $$('#cname').val();
        var customer = $$('#customer').val();
        var message = $$('#message').val();
        
        if (isEmpty(cname)==false&&isEmpty(message)==false){
            $$.post('cAction', {cname:cname,customer:customer,message:message,bdate:bdate,edate:edate,action:'draft'}, function (data) {

                //var obj=eval('('+data+')');
            }); 
        }
        else{
            myApp.alert('您未完全填写','');
        }

    });

}

