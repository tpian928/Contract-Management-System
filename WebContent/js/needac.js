window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

    //点击分配按钮
    $$(document).on('click','a', function () {
        var cid = $$(this).prop('id');
        var cname = $$('strong').html();

        location.href="/CM/ac?cid="+cid+"&cname="+cname+"action=ac";

    });

}

