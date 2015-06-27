window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;
    var myApp = new Framework7();
    //禁用input
    $$('#cname').prop('disabled', true);
    $$('#customer').prop('disabled', true);
    $$('#input_01').prop('disabled', true);
    $$('#input_02').prop('disabled', true);
    $$('#message').prop('disabled', true);

}