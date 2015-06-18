window.onload = initHtml;

function initHtml () {
	var $$ = Dom7;
	$$('#addRole').on('click', function () {
		location.href="addRole.html"
	});
}
