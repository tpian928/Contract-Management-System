window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

	$$(document).on('click','#spsubmit', function () {


		var sps = $$('#sps').val();

        var agree = $$('#agree').prop('checked');
        var disagree = $$('#disagree').prop('checked');

		var myApp = new Framework7();

        if (isEmpty(sps)==false){

            if (agree==false&&disagree==false) {
                myApp.alert('您未完全填写','');
            }
            else{
                $$.post('cAction', {sps:sps,agree:agree,action:'spaction'}, function (data) {
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

        }
        else{
            myApp.alert('您未完全填写','');
        }

	});	

}

