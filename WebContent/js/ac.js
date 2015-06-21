window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;


    $$('#hq').on('click', function () {

        $$.get('/CM/ac', {page:'0'}, function (data) {
            x(data);
        }); 

    });

    $$('#sp').on('click', function () {

        $$.get('/CM/ac', {page:'1'}, function (data) {
            x(data);
        }); 

    });

    $$('#qd').on('click', function () {

        $$.get('/CM/ac', {page:'2'}, function (data) {
            x(data);
        }); 

    });

}

