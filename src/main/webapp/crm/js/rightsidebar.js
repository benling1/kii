$(function(){
	var timer=null;
	$('.rightsidebarDiv').mouseover(function(){
		var that=$(this);
		that.addClass('on');	
		
	});
	$('.rightsidebarDiv').mouseout(function(){
		var that=$(this);
		that.removeClass('on');	
	
		
	});
	$('.blackTop').click(function(){
   		$('body,html').animate({scrollTop:0},500);
	});
	$('.yijianfankui').click(function(){
		$('.yijianfankuiBox').show();
		$('.markBg').show();
	});
	$('.titleH3 em').click(function(){
		$('.yijianfankuiBox').hide();
		$('.markBg').hide();
	});
	$('.uploadImgBox').on('click','.closeImg',function(){
		$(this).parents('li').remove();
		$('#uploadInput').val('');
	});
	$('.uploadImgA').click(function(){
		$('#uploadInput').click();
	});
	$('.bodyBtn a').click(function(){
		var that=$(this);
		if($(this).hasClass('on'))return;
		$(this).addClass('on');	
		var imgLen=$('.img-li img').length;
		var imgSrc=$('.img-li img').prop('src');
		var arr=[];
		for(var i=0;i<imgLen;i++){
			arr.push($('.img-li img').eq(i).prop('src').split(',')[1]);
			
		}


		var str=arr.join(',');
		if($('#feedbackContent').val()==''){
			that.addClass('on');
			$('.msgAlert').text('反馈内容不能为空');
        	var msgAlertH=$('.msgAlert').height();
        	var msgAlertW=$('.msgAlert').width();
        	$('.msgAlert').css({
        		'margin-left':-msgAlertW/2,
        		'margin-top':-msgAlertH/2,
        	});
        	$('.msgAlert').show();
        	setTimeout(function(){
        		$('.msgAlert').hide();
        	},2000);
        	return;
		}
		//使用ajax异步提交
		$.ajax({
			url : $('#src').val()+"/crm/feedBackMessage",
			data : {
				"fileImages" :str,
				feedbackContent:$('#feedbackContent').val(),
				contactMode:$('#contactMode').val(),
			},
			type : "post",
			success : function(data) {
				that.addClass('on');	
				if(!data.message){
					$('.msgAlert').text('提交失败');
		        	var msgAlertH=$('.msgAlert').height();
		        	var msgAlertW=$('.msgAlert').width();
		        	$('.msgAlert').css({
		        		'margin-left':-msgAlertW/2,
		        		'margin-top':-msgAlertH/2,
		        	});
		        	$('.msgAlert').show();
		        	setTimeout(function(){
		        		$('.msgAlert').hide();
		        	},2000);

				}else{
					$('.msgAlert').text('提交成功');
		        	var msgAlertH=$('.msgAlert').height();
		        	var msgAlertW=$('.msgAlert').width();
		        	$('.msgAlert').css({
		        		'margin-left':-msgAlertW/2,
		        		'margin-top':-msgAlertH/2,
		        	});
		        	$('.msgAlert').show();
		        	setTimeout(function(){
		        		$('.msgAlert').hide();
		        	},2000);
		        	$('.yijianfankuiBox').hide();
		        	$('.markBg').hide();
		        	$('#feedbackContent').val('');
		        	$('#imgboxid').html('');
				}
			},
			dataType : 'json'
		})
	})
});
function xmTanUploadImg(obj) {  
    var fl=obj.files.length; 
    var input = document.getElementById("uploadInput");
    if((fl+$('.img-li').length)>5){
    	$('.msgAlert').text('最多上传5张图片');
    	var msgAlertH=$('.msgAlert').height();
    	var msgAlertW=$('.msgAlert').width();
    	$('.msgAlert').css({
    		'margin-left':-msgAlertW/2,
    		'margin-top':-msgAlertH/2,
    	});
    	$('.msgAlert').show();
    	setTimeout(function(){
    		$('.msgAlert').hide();
    	},2000);
    	return;
    }
    for(var g=0;g<obj.files.length;g++){

    	if (!obj.files[g].name.match(/.jpg|.gif|.png|.bmp/)){
    		$('.msgAlert').text('上传的图片格式不正确，请重新选择');
        	var msgAlertH=$('.msgAlert').height();
        	var msgAlertW=$('.msgAlert').width();
        	$('.msgAlert').css({
        		'margin-left':-msgAlertW/2,
        		'margin-top':-msgAlertH/2,
        	});
        	$('.msgAlert').show();
        	setTimeout(function(){
        		$('.msgAlert').hide();
        	},2000);
    		return;
    	}
    	if(obj.files[g].size>=1048576){
    		$('.msgAlert').text('上传的图片单张最大为1MB，请重新上传！');
        	var msgAlertH=$('.msgAlert').height();
        	var msgAlertW=$('.msgAlert').width();
        	$('.msgAlert').css({
        		'margin-left':-msgAlertW/2,
        		'margin-top':-msgAlertH/2,
        	});
        	$('.msgAlert').show();
        	setTimeout(function(){
        		$('.msgAlert').hide();
        	},2000);
    		return;
    	}
    }
    for(var i=0;i<fl;i++){  
        var file=obj.files[i]; 
       
        var reader = new FileReader();  

        //读取文件过程方法  

        reader.onloadstart = function (e) {  

        }  
        reader.onprogress = function (e) {  

        }  
        reader.onabort = function (e) {  

        }  
        reader.onerror = function (e) {  
 
        }  
        reader.onload = function (e) {  
        	
            var imgstr='<img src="'+e.target.result+'"/>';  
            var oimgbox=document.getElementById("imgboxid");  
            var nLi=document.createElement("li");  
            
            nLi.innerHTML=imgstr+'<i class="closeImg"></i>';  
            nLi.className="img-li";  
            oimgbox.appendChild(nLi);  
            
             
        }  

        reader.readAsDataURL(file);  
//alert(1);  
    }  

}  
function dataURItoBlob(base64Data) {
	var byteString;
	if (base64Data.split(',')[0].indexOf('base64') >= 0)
		byteString = atob(base64Data.split(',')[1]);
	else
		byteString = unescape(base64Data.split(',')[1]);
	var mimeString = base64Data.split(',')[0].split(':')[1].split(';')[0];
	var ia = new Uint8Array(byteString.length);
	for (var i = 0; i < byteString.length; i++) {
		ia[i] = byteString.charCodeAt(i);
	}
	return new Blob([ia], {type:mimeString});
}