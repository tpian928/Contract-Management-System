window.onload = initHtml;



function initHtml () {
	
	var $$ = Dom7;

	$$('#addRole').on('click', function () {
		location.href="addRole.html"
	});

	$$('#searchBtn').on('click', function () {
		submit();
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