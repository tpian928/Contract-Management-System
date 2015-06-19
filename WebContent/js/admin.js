window.onload = initHtml;

function initHtml () {
	var $$ = Dom7;

	$$.post('showuser', {access_taken:localStorage.access_taken,id:localStorage.id}, function (data) {
		o(data);
    });  

	$$('#addRole').on('click', function () {
		location.href="addRole.html"
	});


}
