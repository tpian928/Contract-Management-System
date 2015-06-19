window.onload = initHtml;

function initHtml () {
	var $$ = Dom7;

	$$('#addRole').on('click', function () {
		location.href="addRole.html"
	});

	$$('#searchBtn').on('click', function () {
		o("searchBtn Click");
		var name = $$('#name').val();
		$$.get('showuser', {name:name}, function (data) {
			x(data);
		});  
	});	


}
