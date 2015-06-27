window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

	$$('#addRole').on('click', function () {
		location.href="addRole.html"
	});

	$$(document).on('click','#searchBtn', function () {
		o("searchBtn Click");
		
		var name = $$('#name').val();

		window.location.href="/CM/showuser?name="+name

	});	

	$$('a').on('click', function () {
		var grantid = $$(this).attr('id');
		localStorage.grantid=grantid;
		window.location.href="/CM/grant"		
	});	

}
