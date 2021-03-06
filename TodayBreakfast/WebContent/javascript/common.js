
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}



function getURLVar(urlVarName) {
	var urlHalves = String(document.location).toLowerCase().split('?');
	var urlVarValue = '';

	if (urlHalves[1]) {
		var urlVars = urlHalves[1].split('&');

		for (var i = 0; i <= (urlVars.length); i++) {
			if (urlVars[i]) {
				var urlVarPair = urlVars[i].split('=');
				if (urlVarPair[0] && urlVarPair[0] == urlVarName.toLowerCase()) {
					urlVarValue = urlVarPair[1];
				}
			}
		}
	}

	return urlVarValue;
} 

$(document).ready(function() {
	route = getURLVar('route');

	if (!route) {
		$('#tab_home').addClass('selected');

	} else {
		part = route.split('/');

		if (route == 'common/home') {
			$('#tab_home').addClass('selected');
		}else if(route == 'product/home'){
			$('#tab_products').addClass('selected');
		}else if(route =='information/information'){
			path=getURLVar('information_id');

			if(path==29 || path==30 || path==31){
				$('#tab_news').addClass('selected');
			}
			
			if(path==32 || path==33 || path==34 || path==35 || path==36){
				$('#tab_partenr').addClass('selected');
			}
			
			if(path==28){
				$('#tab_about').addClass('selected');
			}
		}else if(route =='information/article/category'){
			path=getURLVar('article_category_id');

			if(path==5){
				$('#tab_zhuanjia').addClass('selected');
			}
			
			if(path==6){
				$('#tab_share').addClass('selected');
			}
		}
	}
});


$(document).ready(function() {
	/* Search 
	$('.button-search').bind('click', function() {
		url = 'index.php?route=product/search';
		 
		var filter_name = $('input[name=\'filter_name\']').attr('value')
		
		if (filter_name) {
			url += '&filter_name=' + encodeURIComponent(filter_name);
		}
		
		location = url;
	});
	
	$('#header input[name=\'filter_name\']').keydown(function(e) {
		if (e.keyCode == 13) {
			url = 'index.php?route=product/search';
			 
			var filter_name = $('input[name=\'filter_name\']').attr('value')
			
			if (filter_name) {
				url += '&filter_name=' + encodeURIComponent(filter_name);
			}
			
			location = url;
		}
	});
*/
	
	/* Ajax Cart */
	$('#cart > .heading').bind('mouseenter mouseover', function() {
		$('#cart').addClass('active');
		
		$.ajax({
			url: 'index.php?route=checkout/cart/update',
			dataType: 'json',
			success: function(json) {
				if (json['output']) {
					$('#cart .content').html(json['output']);
				}
			}
		});			
		
		$('#cart').bind('mouseleave', function() {
			$(this).removeClass('active');
		});
	});
	
	/* Mega Menu */
	$('#menu ul > li > a + div').each(function(index, element) {
		// IE6 & IE7 Fixes
		if ($.browser.msie && ($.browser.version == 7 || $.browser.version == 6)) {
			var category = $(element).find('a');
			var columns = $(element).find('ul').length;
			
			$(element).css('width', (columns * 143) + 'px');
			$(element).find('ul').css('float', 'left');
		}		
		
		var menu = $('#menu').offset();
		var dropdown = $(this).parent().offset();
		
		i = (dropdown.left + $(this).outerWidth()) - (menu.left + $('#menu').outerWidth());
		
		if (i > 0) {
			$(this).css('margin-left', '-' + (i + 5) + 'px');
		}
	});

	// IE6 & IE7 Fixes
	if ($.browser.msie) {
		if ($.browser.version <= 6) {
			$('#column-left + #column-right + #content, #column-left + #content').css('margin-left', '195px');
			
			$('#column-right + #content').css('margin-right', '195px');
		
			$('.box-category ul li a.active + ul').css('display', 'block');	
		}
		
		if ($.browser.version <= 7) {
			$('#menu > ul > li').bind('mouseover', function() {
				$(this).addClass('active');
			});
				
			$('#menu > ul > li').bind('mouseout', function() {
				$(this).removeClass('active');
			});	
		}
	}
});

$('.success img, .warning img, .attention img, .information img').live('click', function() {
	$(this).parent().fadeOut('slow', function() {
		$(this).remove();
	});
});

/* add cart animation*/
function addCartAnimation(product_id, bf_type, total) {
    var cart = $('#cart_total');
    var pro = '#pdt_img'+product_id+'_'+bf_type;
    var imgtodrag = $(pro);
    if (imgtodrag) {
        var imgclone = imgtodrag.clone()
            .offset({
            top: imgtodrag.offset().top,
            left: imgtodrag.offset().left
        })
            .css({
            'opacity': '0.5',
                'position': 'absolute',
                'height': imgtodrag.height()/2+'px',
                'width': imgtodrag.width()/2+'px',
                'z-index': '3000'
        })
            .appendTo($('body'))
            .animate({
                 'top': cart.offset().top,
                'left': cart.offset().left,
                'width': '10px',
                'height': '10px'
        },   { duration:500,
        	   easing: 'easeInOutQuad',
        	   complete:  function (){
                  cart.effect("shake", {
                     times: 2,
                     distance: 15
                  }, 100);
                  $('#cart_total').html(total);
                  $(this).detach()
        	   }       
        });
    }
};
  

function sendValidationCode() {
	 var cellphonenumber = $('#register\\:cellphone').val();
	 if (cellphonenumber == 'undefined' || cellphonenumber =='') {
		 $('#mobile-error').css('display','block');
		 return;
	 }
	$.ajax({
		url: 'AjaxRequest?',
		type: 'post',
		data: 'action=sendValidationCode&cellphone=' + cellphonenumber,
		dataType: 'json',
		success: function(json) {
	
			$('.success, .warning, .attention, .information, .error').remove();
				
			if (json['errcode'] == 0) {
				$('#get-vcode-btn').attr('disabled',"true"); 
			} else if (json['errcode'] == 1) {
			}
		}
	});
	
};


/* 加入购物车*/
function addToCart2(product_id, bf_type) {
	
	$.ajax({
		url: 'AjaxRequest?',
		type: 'post',
		data: 'action=addCart&bf_id=' + product_id+ '&type=' + bf_type,
		dataType: 'json',
		success: function(json) {
		
	
			$('.success, .warning, .attention, .information, .error').remove();
			
				
			if (json['errcode'] == 0) {
				
					addCartAnimation(product_id,bf_type,json['total']);
			
			} else if (json['errcode'] == 1) {
				//TODO alert error message
			}
		}
	});
	
}


/* 加入购物车*/
function addToCart(product_id,promotion_code) {

	var quantity = $('#product_'+product_id+'_quantity').val();
		
	quantity=typeof(quantity) != 'undefined' ? quantity : 1;
	
	promotion_code=typeof(promotion_code) != 'undefined' ?  promotion_code: '';

	$.ajax({
		url: 'index.php?route=checkout/cart/update',
		type: 'post',
		data: 'product_id=' + product_id+ '&quantity=' + quantity+ '&promotion_code=' + promotion_code,
		dataType: 'json',
		success: function(json) {
		
		//alert(json);
			$('.success, .warning, .attention, .information, .error').remove();
			
			
			//document.write(json);
			if (json['error']) {
				if (json['error']['warning']) {
					$('#notification').html('<div class="warning" style="display: none;">' + json['error']['warning'] + '<img src="catalog/view/theme/default/image/close.png" alt="" class="close" /></div>');
				}
				
				if (json['error']['alert']) {
					alert(json['error']['alert']);
					return false;
				}
				
			}	
			
			if (json['redirect']) {
				location = json['redirect'];
			}
						
			if (json['success']) {
				
				//location = window.location;

				// $('#notification').html('<div class="attention" style="display: none;">' + json['success'] + '<img src="catalog/view/theme/default/image/close.png" alt="" class="close" /></div>');
				
				// $('.attention').fadeIn('slow');
				
				//$('#cart_total').html(json['total']);
				
				// 部分微信jquery调用有问题，解决后删除
				//if(isWeiXin())
				//{
					//var d=new Date();
					//alert('欢迎您用微信访问青年菜君'+d.getTime());	
				//	$('#cart_total').effect("shake", {
	            //        times: 2
	            //         }, 100);
	                  
	        	 //     $('#cart_total').html(json['total']);					
					
				//}
				//else{
					// 加入购物车动画效果
					addCartAnimation(product_id,json['total']);
				//}
			}	
		}
	});
}

function shopcart_addToCart(product_id){
	var quantity = $('#product_'+product_id+'_quantity').val();
	
	quantity=typeof(quantity) != 'undefined' ? quantity : 1;
	
	$.ajax({
		url: 'index.php?route=checkout/cart/update',
		type: 'post',
		data: 'product_id=' + product_id+ '&quantity=' + quantity,
		dataType: 'json',
		success: function(json) {
			$('.success, .warning, .attention, .information, .error').remove();
			
			if (json['error']) {
				if (json['error']['warning']) {
					$('#notification').html('<div class="warning" style="display: none;">' + json['error']['warning'] + '<img src="catalog/view/theme/default/image/close.png" alt="" class="close" /></div>');
				}
				
				if (json['error']['alert']) {
					alert(json['error']['alert']);
					return false;
				}
				
			}	
			
			if (json['redirect']) {
				location = json['redirect'];
			}
						
			if (json['success']) {
				location = window.location;
			}	
		}
	});
}
function shopcart_downToCart(key){
	shopcart_ToCart(key,-1);
}
function shopcart_upToCart(key){
	shopcart_ToCart(key,1);
}

function shopcart_ToCart(key,quantity){
	// var quantity = $('#product_'+product_id+'_quantity').val();
	
	// quantity=typeof(quantity) != 'undefined' ? quantity : 1;
	
	$.ajax({
		url: 'index.php?route=checkout/cart/update',
		type: 'post',
		data: 'product_key=' + key+ '&quantity=' + quantity,
		dataType: 'json',
		success: function(json) {
			$('.success, .warning, .attention, .information, .error').remove();
			
			if (json['error']) {
				if (json['error']['warning']) {
					$('#notification').html('<div class="warning" style="display: none;">' + json['error']['warning'] + '<img src="catalog/view/theme/default/image/close.png" alt="" class="close" /></div>');
				}
				
				if (json['error']['alert']) {
					alert(json['error']['alert']);
					return false;
				}
				
			}	
			
			if (json['redirect']) {
				location = json['redirect'];
			}
						
			if (json['success']) {
				location = window.location;
			}	
		}
	});
}

function removeCart(key) {
	$.ajax({
		url: 'index.php?route=checkout/cart/update',
		type: 'post',
		data: 'remove=' + key,
		dataType: 'json',
		success: function(json) {
			$('.success, .warning, .attention, .information').remove();
			
			if (json['output']) {
				location = window.location;
				// $('#cart_total').html(json['total']);
				
				// $('#cart .content').html(json['output']);
			}			
		}
	});
}

function removeVoucher(key) {
	$.ajax({
		url: 'index.php?route=checkout/cart/update',
		type: 'post',
		data: 'voucher=' + key,
		dataType: 'json',
		success: function(json) {
			$('.success, .warning, .attention, .information').remove();
			
			if (json['output']) {
				$('#cart_total').html(json['total']);
				
				$('#cart .content').html(json['output']);
			}			
		}
	});
}

function addToWishList(product_id) {
	$.ajax({
		url: 'index.php?route=account/wishlist/update',
		type: 'post',
		data: 'product_id=' + product_id,
		dataType: 'json',
		success: function(json) {
			$('.success, .warning, .attention, .information').remove();
						
			if (json['success']) {
				$('#notification').html('<div class="attention" style="display: none;">' + json['success'] + '<img src="catalog/view/theme/default/image/close.png" alt="" class="close" /></div>');
				
				$('.attention').fadeIn('slow');
				
				$('#wishlist_total').html(json['total']);
				
				$('html, body').animate({ scrollTop: 0 }, 'slow'); 				
			}	
		}
	});
}

function addToCompare(product_id) { 
	$.ajax({
		url: 'index.php?route=product/compare/update',
		type: 'post',
		data: 'product_id=' + product_id,
		dataType: 'json',
		success: function(json) {
			$('.success, .warning, .attention, .information').remove();
						
			if (json['success']) {
				$('#notification').html('<div class="attention" style="display: none;">' + json['success'] + '<img src="catalog/view/theme/default/image/close.png" alt="" class="close" /></div>');
				
				$('.attention').fadeIn('slow');
				
				$('#compare_total').html(json['total']);
				
				$('html, body').animate({ scrollTop: 0 }, 'slow'); 
			}	
		}
	});
}

//Bookmark
function bookmarksite(title,url){
	if (document.all) { //ie
		window.external.AddFavorite( url, title);
	} else if ( window.sidebar ) {// firefox
		window.sidebar.addPanel(title, url,"");
	}else if(window.opera && window.print){
		var elem = document.createElement('a');
		elem.setAttribute('href',url);
		elem.setAttribute('title',title);
		elem.setAttribute('rel','sidebar');
		elem.click();
	} else {
		alert("您的浏览器不支持此操作！\n 请使用Ctrl+D收藏本站。");  
	}
	return ;
}

var Siteilex= window.Siteilex || {};
Siteilex.toolkit={};

Siteilex.toolkit={
	getURLVar:function(urlVarName) {
		var urlHalves = String(document.location).toLowerCase().split('?');
		var urlVarValue = '';

		if (urlHalves[1]) {
			var urlVars = urlHalves[1].split('&');

			for (var i = 0; i <= (urlVars.length); i++) {
				if (urlVars[i]) {
					var urlVarPair = urlVars[i].split('=');

					if (urlVarPair[0] && urlVarPair[0] == urlVarName.toLowerCase()) {
						urlVarValue = urlVarPair[1];
					}
				}
			}
		}

		return urlVarValue;
	},
	
	getUrlParameters:function(){
		var urlHalves = String(document.location).toLowerCase().split('?');
		
		var urlVarValue = '';
		
		if (urlHalves[1]) {
			urlVarValue=urlHalves[1];
		}else{
			urlVarValue='';
		}
		return urlVarValue;
	},
	
	init_menu:function(node,className,parentNodeType){
			$(document).ready(function() {
				route = Siteilex.toolkit.getUrlParameters();

				if (!route) {
					$(d).addClass(className);
				} else {
					url=route;
					parentNodeType ? $('a[href*=\'' + url + '\']').parents(parentNodeType).addClass(className):$('a[href*=\'' + url + '\']').addClass(className)
				}
			});
	}
};

function refreshCaptcha(name,url){
	var captcha_link=url+'&'+Date.parse(new Date());
	$('#'+name).attr('src',captcha_link);
}

function display(view) {
	if (view == 'list') {
		$('.product-grid').attr('class', 'product-list');
		
		$('.product-list > div').each(function(index, element) {
			html  = '<div class="right">';
			html += '  <div class="cart">' + $(element).find('.cart').html() + '</div>';
			html += '  <div class="wishlist">' + $(element).find('.wishlist').html() + '</div>';
//			html += '  <div class="compare">' + $(element).find('.compare').html() + '</div>';
			html += '</div>';			
			
			html += '<div class="left">';
			
			var image = $(element).find('.image').html();
			
			if (image != null) { 
				html += '<div class="image">' + image + '</div>';
			}
			
			var price = $(element).find('.price').html();
			
			if (price != null) {
				html += '<div class="price">' + price  + '</div>';
			}
						
			html += '  <div class="name">' + $(element).find('.name').html() + '</div>';
			html += '  <div class="description">' + $(element).find('.description').html() + '</div>';
			
			var rating = $(element).find('.rating').html();
			
			if (rating != null) {
				html += '<div class="rating">' + rating + '</div>';
			}
				
			html += '</div>';

						
			$(element).html(html);
		});		
		
		$('.display .grid').removeClass('active');
		$('.display .list').addClass('active');
		
		$.cookie('display', 'list'); 
	} else {
		$('.product-list').attr('class', 'product-grid');
		
		$('.product-grid > div').each(function(index, element) {
			html = '<div class="product-box">';
			
			var image = $(element).find('.image').html();
			
			if (image != null) {
				html += '<div class="image">' + image + '</div>';
			}
			
			html += '<div class="name">' + $(element).find('.name').html() + '</div>';
			html += '<div class="description">' + $(element).find('.description').html() + '</div>';
			
			var price = $(element).find('.price').html();
			
			if (price != null) {
				html += '<div class="price">' + price  + '</div>';
			}	
					
			var rating = $(element).find('.rating').html();
			
			if (rating != null) {
				html += '<div class="rating">' + rating + '</div>';
			}
						
			html += '<div class="operate">';
			html += '<div class="cart">' + $(element).find('.cart').html() + '</div>';
			html += '<div class="wishlist">' + $(element).find('.wishlist').html() + '</div>';
//			html += '<div class="compare">' + $(element).find('.compare').html() + '</div>';
			html += '</div>';
			html += '</div>';
			$(element).html(html);
		});	
					
		$('.display .grid').addClass('active');
		$('.display .list').removeClass('active');
		
		$.cookie('display', 'grid');
	}
}

//update product quantity 
function update_product_quantity(prefix,productid,doType,qtyMin,qtyMax){
	var qtyObject = $('#'+prefix+productid+'_quantity');
	var qtyNum = qtyObject.val(); 
	var status = true;
	if( !isNaN(qtyNum)) {
		qtyNum = parseInt(qtyNum);
		qtyMin = parseInt(qtyMin);
		qtyMax = 99;//parseInt(qtyMax);
		oriNum = parseInt(qtyObject.attr('orinum'));
		updateNum = qtyNum;
		if(doType=='up'){
			if(qtyMax>0){
				if(qtyNum<qtyMax){
					updateNum = qtyNum+1;
				}
			} else{
				updateNum = qtyNum+1;
			}
		} else if(doType=='down'){
			if(qtyMin>0){
				if(qtyNum>qtyMin){
					updateNum = qtyNum-1;
				}
			} else{
				updateNum = qtyNum-1;
			}
		} else if(doType=='blur'){
			updateNum = qtyNum;
		}
		if(oriNum==updateNum||updateNum<qtyMin||updateNum>qtyMax) status = false;
		if(status){
			qtyObject.val(updateNum);
			qtyObject.attr('orinum',updateNum);
		} else{
			qtyObject.val(oriNum);
			qtyObject.attr('orinum',oriNum);
			/*remind_show_id = prefix+productid+'_remind';
			qtyObject.after('<span class="remind" id="'+remind_show_id+'">输入有误！</span>');
			window.setTimeout($("#"+remind_show_id).fadeOut(), 5000);*/
		}
	}
	return status;
}



$(document).ready(function() {
	formauto ();
});


function formauto (){

	//select
	$('select').each(function(){
		if($(this).attr('thevalue')!='' && $(this).attr('thevalue')!=null){
			$(this).val($(this).attr('thevalue'));
		}
	});

	//checkbox
	$('input:checkbox').each(function(){

		if($(this).attr('thevalue')!='' && $(this).attr('thevalue')!=null){

			var myarray = $(this).attr('thevalue');
			var value = $(this).attr('value');

			if(jQuery.inArray(value, myarray)!==-1){
				$(this).prop( 'checked', true );
				// console.log('yo');
			}
		}


	});
	//radio
	$('input:radio').each(function(){

		if($(this).attr('thevalue')!='' && $(this).attr('thevalue')!=null){

			var thevalue = $(this).attr('thevalue');
			var value = $(this).attr('value');

			if(value==thevalue){
				$(this).prop( 'checked', true );

			}
		}


	});
}









