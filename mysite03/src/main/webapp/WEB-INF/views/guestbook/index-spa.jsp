<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite03</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var render = function(vo){
		var html = 
			"<li data-no='" + vo.no + "'>" +
			"<strong>" + vo.name + "</strong>" +
			"<p>" + vo.message.replace(/\n/g,"<br>") + "</p>" +
			"<strong></strong>" +
			"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
			"</li>";
			
			return html;
};
var fetch = function(){
	
	$.ajax({
		url: "${pageContext.request.contextPath }/api/guestbook",
		asnyc: true,
		type: 'get',
		dataType: 'json',
		success: function(response){
			if(response.result !== 'success'){
				console.error(response.message);	
				return;
			}
			
			for(var i = 0; i< response.data.length; i++){
				var vo = response.data[i];
				//console.log(i, vo);
				var html = render(vo);
				$("#list-guestbook").append(html);
				startNo = response.data[i].no;
				//console.log("startNo : " + startNo);
			}
		}
	});
};

$(function(){
		
	// add event 
	$("#add-form").submit(function(event){
		event.preventDefault();
		
		var vo = {};
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.message = $("#tx-content").val();
		
		console.log(vo);
		
		$.ajax({
			url: '${pageContext.request.contextPath }/api/guestbook',
			type: 'post',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(vo),	//data를 string화 시키기 
			success: function(response){
				if(response.result !== 'success'){
					console.error(response.message);
					return;
				}
				
				var html = render(response.data);
				$("#list-guestbook").prepend(html);
				
				$("#input-name").val("");
				$("#input-password").val("");
				$("#tx-content").val("");
			}
			
			
		});

	});
	
	// 삭제 다이얼로그 객체 생성 
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons:{
			"삭제": function(){
				var no = $("#hidden-no").val();
				var password = $("#password-delete").val();
				var url = "${pageContext.request.contextPath }/api/guestbook/" + no;
				$.ajax({
					url: url,
					async: true,
					type: 'delete',
					dataType: 'json',
					data: "password="+ password,
					success: function(response){
						if(response.result !== 'success'){
							console.error(response.message);
							return;
						}
						
						if(response.data == -1){
							$(".validateTips.error").show();
							$("#password-delete").val("").focus();
							return;
						}
						
						// 삭제 된 경우
						$("#list-guestbook li[data-no='"+ response.data + "']").remove();
						dialogDelete.dialog('close');
					}
				});
			},
			"취소": function(){
				$(this).dialog('close');
			}
		},
		close:function(){

			$(".validateTips.error").hide();
			$("#password-delete").val("");
			$("#hidden-no").val("");
		}
		
	});
	
	// 스크롤 이벤트 
	$(window).scroll(function(){
		var $window = $(this);
		var windowHeight = $window.height();
		var scrollTop = $window.scrollTop();
		var documentHeight = $(document).height();
		if(scrollTop + windowHeight + 10 > documentHeight){
			console.log("scrollTop + windowHeight + 10 > documentHeight");
		}
	});
	
	// 글 삭제 버튼 Click event 처리 (Live Event)
	$(document).on('click',"#list-guestbook li a", function(event){
		event.preventDefault();
		
		var no = $(this).data("no");
		$("#hidden-no").val(no);
		console.log("clicked");
		dialogDelete.dialog('open');
	});
	
	// 최초 리스트 가져오기
	fetch();
	
	
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				
				<div>
					<button id="btn-next" title="다음 가져오기">next</button>
				</div>
				
				<ul id="list-guestbook"></ul>
				
				<div>
					<button id="btn-next" title="다음 가져오기">next</button>
				</div>
				
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>