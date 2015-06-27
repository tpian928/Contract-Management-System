window.onload = initHtml;

function initHtml () {
	
	var $$ = Dom7;

    var tmpuserid = 0;
    var tmptype = 0;
    var tmpName = "";

    $$('#ac1').prop('disabled',true);
    $$('#ac2').prop('disabled',true);
    $$('#ac3').prop('disabled',true);

    //分配按钮的点击
    $$('#ac1').on('click', function () {

        if ($$('#ac1').prop('value')=="分配") {
            var str = "<li class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title fhq\" id=\""+tmpuserid+"\">"+tmpName+"</div></div></li>"
            $$('#willhq').append(str);
            $$('.item-title').each(
                function() {
                    if ($$(this).hasClass('clickbackgroud')) {
                        $$(this).parent().parent().remove(); 
                    }
                }        
            );
        }
        else{
            var str = "<li class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title hq\" id=\""+tmpuserid+"\">"+tmpName+"</div></div></li>"
            $$('#hq').append(str);
            $$('.item-title').each(
                function() {
                    if ($$(this).hasClass('clickbackgroud')) {
                        $$(this).parent().parent().remove(); 
                    }
                }        
            );
        }

        $$('#ac1').prop('disabled',true);

    });
    $$('#ac2').on('click', function () {

        if ($$('#ac2').prop('value')=="分配") {
            var str = "<li class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title fsp\" id=\""+tmpuserid+"\">"+tmpName+"</div></div></li>"
            $$('#willsp').append(str);
            $$('.item-title').each(
                function() {
                    if ($$(this).hasClass('clickbackgroud')) {
                        $$(this).parent().parent().remove(); 
                    }
                }        
            );
        }
        else{
            var str = "<li class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title sp\" id=\""+tmpuserid+"\">"+tmpName+"</div></div></li>"
            $$('#sp').append(str);
            $$('.item-title').each(
                function() {
                    if ($$(this).hasClass('clickbackgroud')) {
                        $$(this).parent().parent().remove(); 
                    }
                }        
            );            
        }

        $$('#ac2').prop('disabled',true);
    });
    $$('#ac3').on('click', function () {

        if ($$('#ac3').prop('value')=="分配") {
            var str = "<li class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title fqd\" id=\""+tmpuserid+"\">"+tmpName+"</div></div></li>"
            $$('#willqd').append(str);
            $$('.item-title').each(
                function() {
                    if ($$(this).hasClass('clickbackgroud')) {
                        $$(this).parent().parent().remove(); 
                    }
                }        
            );            
        }
        else{
             var str = "<li class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title qd\" id=\""+tmpuserid+"\">"+tmpName+"</div></div></li>"
            $$('#qd').append(str);
            $$('.item-title').each(
                function() {
                    if ($$(this).hasClass('clickbackgroud')) {
                        $$(this).parent().parent().remove(); 
                    }
                }        
            );            
        }

        $$('#ac3').prop('disabled',true);
    });


    $$(document).on('click','.item-title', function () {

        //改变背景颜色
        $$('.item-title').each(
            function() {
                $$(this).removeClass('clickbackgroud'); 
            }        
        );

        $$(this).addClass('clickbackgroud');

        
        tmpuserid=$$(this).prop('id');
        tmpName = $$(this).html()

        //会签，审批，签订的编号
        if ($$(this).hasClass('hq')) {
            tmptype=0;
            $$('#ac1').prop('disabled',false);
            $$('#ac2').prop('disabled',true);
            $$('#ac3').prop('disabled',true);
            $$('#ac1').prop('value','分配')
        }
        else if($$(this).hasClass('sp')){
            tmptype=1;
            $$('#ac1').prop('disabled',true);
            $$('#ac2').prop('disabled',false);
            $$('#ac3').prop('disabled',true);
            $$('#ac2').prop('value','分配')
        }
        else if ($$(this).hasClass('qd')) {
            tmptype=2;
            $$('#ac1').prop('disabled',true);
            $$('#ac2').prop('disabled',true);
            $$('#ac3').prop('disabled',false);
            $$('#ac3').prop('value','分配')
        }
        else if ($$(this).hasClass('fhq')) {
            tmptype=0;
            $$('#ac1').prop('disabled',false);
            $$('#ac2').prop('disabled',true);
            $$('#ac3').prop('disabled',true);
            $$('#ac1').prop('value','反分配')
        }
        else if ($$(this).hasClass('fsp')) {
            tmptype=1;
            $$('#ac1').prop('disabled',true);
            $$('#ac2').prop('disabled',false);
            $$('#ac3').prop('disabled',true);
            $$('#ac2').prop('value','反分配')
        }
        else if ($$(this).hasClass('fqd')) {
            tmptype=2;
            $$('#ac1').prop('disabled',true);
            $$('#ac2').prop('disabled',true);
            $$('#ac3').prop('disabled',false);
            $$('#ac3').prop('value','反分配')
        }

    });

     //提交按钮
    $$(document).on('click','#acsubmit', function () {

        var hqUserStr ="";
        var spUserStr ="";
        var qdUserStr ="";

        $$('.item-title').each(
            function() {
                if ($$(this).hasClass('fhq')) {
                    o("willhq");
                    hqUserStr=hqUserStr+"-"+$$(this).prop('id');
                }
                else if ($$(this).hasClass('fsp')){
                    o("willsp");
                    spUserStr=spUserStr+"-"+$$(this).prop('id');
                }
                else if ($$(this).hasClass('fqd')){
                    o("willsp");
                    qdUserStr=qdUserStr+"-"+$$(this).prop('id');
                }
            }        
        );

        $$.post('cAction', {hqUserStr:hqUserStr,spUserStr:spUserStr,qdUserStr:qdUserStr,action:'ac'}, function (data) {
            var obj=eval('('+data+')');
            var myApp = new Framework7();
            if (obj.result) {
                myApp.alert('添加成功', '', function () {
                    window.location.herf="/CM/index";
                });
            }
            else{
                myApp.alert('添加失败','');
            }
        }); 

    });

    $$('#clear').on('click', function () {
        location.reload();
    });

}

