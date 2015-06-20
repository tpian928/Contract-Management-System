window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

	$$('#addRole').on('click', function () {
		location.href="addRole.html"
	});

	$$('#searchBtn').on('click', function () {
		submit();
	});	

	$$('a').on('click', function () {
		var grantid = $$(this).attr('id');
		localStorage.grantid=grantid;
		location.href="/CM/grant"		
	});	

}

function submit () {
	o("searchBtn Click");
	var $$ = Dom7;
	var name = $$('#name').val();
	$$.get('showuser', {name:name}, function (data) {
		x(data);
	});  
}