window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

    var nowurl = window.location.href;
	var query = $$.parseUrlQuery(nowurl);
	var pageStr = $$.serializeObject(query.page);
	if (pageStr==""||pageStr=="undefined") {
		pageStr="0";
	}
	$$('#page').val(pageStr);

	var q = $$('#q').val();

    //主界面搜索
    $$(document).on('click','#searchBtn', function () {
        window.location.href="/CM/index?page="+pageStr+"&q="+q;
    });

}

