
		$(function(){
			
			
			/*点击开关提醒*/
//			$(".green_check").click(function(){
//				if (   $(this).attr("src")=="images/green_check.png"   ) {
//					$(this).attr({"src":"images/green_uncheck.png"})
//				} else{
//					$(this).attr({"src":"images/green_check.png"})
//				}
//			});
//			
//			/*点击顶部设置按钮显示右边框*/
//			$(".right_top_set_btn").click(function(){
//				$(".right_top_set").toggle()
//			});
//			
//			/*顶部时间显示*/
//			setInterval(function() {
//		    	var now = (new Date()).toLocaleString();
//		   		$('.time').text(now);
//			}, 1000);
		
		
		
		
		
			$(".more_data").click(function(){
				$(this).parent().next().children(".more_data_in").toggle();
			
				
				if (   $(this).text()=="显示更多数据"   ) {
					$(this).text("隐藏更多数据")
				} else{
					$(this).text("显示更多数据")
				}
				
				
			});
			
			
			$(".closeHuoDong").click(function(){
				$(".11huodong").hide();
			});
		
		 
		
		
			$(".data1").hover(function(){
			    $(this).children(".data1_1").addClass("bgc_4ecdff c_fff").removeClass("bgc_f8f9fc c_384856");
			    $(this).children(".data1_2").addClass("c_4ecdff ").removeClass("c_384856");
				},function(){
			    $(this).children(".data1_1").addClass("bgc_f8f9fc c_384856").removeClass("bgc_4ecdff c_fff");
				$(this).children(".data1_2").addClass("c_384856").removeClass("c_4ecdff");
			});
			
			$(".data2").hover(function(){
			    $(this).children(".data2_1").addClass("bgc_ff813e c_fff").removeClass("bgc_f8f9fc c_384856");
			    $(this).children(".data2_2").addClass("c_ff813e ").removeClass("c_384856");
				},function(){
			    $(this).children(".data2_1").addClass("bgc_f8f9fc c_384856").removeClass("bgc_ff813e c_fff");
				$(this).children(".data2_2").addClass("c_384856").removeClass("c_ff813e");
			});
		
		
			
		
		
		
		
			/* --暂时注释
			 * $(".close").click(function(){
				
				
			var re=/[1-9][0-9]{4,}/;
			
			var phone=/^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
			
			
			if ($(".PH").val().match(phone)&&$(".YZ").val()!=""&&$(".QQ").val().match(re)&&$(".PH").val()!=""&&$(".QQ").val()!="") {
				
				$(".guide_img").hide();
			} else{
				return false;
			}
				
				
			})*/
			
			
			
			
			
			/*//点击获取验证码出现倒计时
            $('.HQ').click(function(){
            	var phone=$(".SJ").val();
           	var re = /^1\d{10}$/;
           	if(re.test(phone)){
           		$('.HQ').hide();
  				$('.JS').show();
  				
            	waitTime();
  					}
           	else{
           		return false;
           	}
            	
            })		
            //60秒倒计时函数
			function waitTime(){
				var T=60;
				timer=null;
				timer=setInterval(function(){
					T--;
					document.getElementById('timer').innerHTML=T;
					if(T==0){
						clearInterval(timer);
						document.getElementById('timer').innerHTML=60;
						$('.JS').hide();
						$('.HQ').show();
					}	
				},1000)
			}
			*/
			
			
			$(".close_xszy").click(function(){
				$(".xszy_").hide();
			});
			
			
				
			
		
		
		});