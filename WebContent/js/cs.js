window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

	$$(document).on('click','#hqsubmit', function () {

		var hqs = $$('#hqs').val();
		o("hqs");

		var myApp = new Framework7();

        if (isEmpty(hqs)==false){
            $$.post('cAction', {hqs:hqs,action:'csaction'}, function (data) {
                o(data);
                var obj=eval('('+data+')');
                var myApp = new Framework7();
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

