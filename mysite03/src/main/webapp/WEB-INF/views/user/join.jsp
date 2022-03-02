<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<title>mysite03</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
<script>
var messageBox = function(title,message, callback){
	$("#dialog-message p").text(message);
	
	$("#dialog-message")
			.attr('title',title)
			.dialog({
	      resizable: false,
	      height: "auto",
	      width: 300,
	      modal: true,
	      buttons: {
	        "Confirm": function() {
	          $( this ).dialog( "close" );
	        }
	      },
	      close: callback
	});
	
}

$(function(){
	$("#join-form").submit(function(){
		event.preventDefault(); //submit 안 되게 방지
		
		// 1. 이름 유효성 체크 - validation(이름이 비어있는지 확인)  
		if($("#name").val() === ''){
			//alert("Name is empty");
			messageBox('Join','Name is required', function(){
				$("#name").focus();
			});
			
			return;
		}
		
		
		// 2. 이메일 유효성 체크 - validation(email empty)
		if($("#email").val() === ''){
			//alert("Email is empty");
			messageBox('Email','Your Email is empty, Email is required',function(){
				$("#email").focus();	
			});
			
			return;
		}
		
		
		// 3. 중복체크 되었는지의 유무 *****
		if($("img", $("#user")).css('display')==='none'){
			//alert("Please check your email is overlapped");
			messageBox('Email','Please check your email is overlapped',function(){
				$("#email").focus();	
			});
			
			return;
		}
		
		// 4. 비밀번호 유효성(empty) 체크
		if($("#password").val() === ''){
			//alert("password is empty");
			messageBox('Password','Password is empty',function(){
				$("#password").focus();	
			});
			
			return;
		}
		
		// 5. 성별 유효성 체크
		if(!$("#gender-field input").is(":checked")){
			//alert("Please check your gender field");
			messageBox('Gender','Please check your gender field');
			return;
		}
		
		// 6. 약관동의 체크 여부
		if(!$("#agree-prov").is(":checked")){
			//alert("Please agree to the provision for use");
			messageBox('agree provision','Please agree to the provision for use');
			$("#agree-prov").focus();
			return;
		}
		
		// 7. 유효성 ok
		console.log("ok!!!");
		//$("#join-form").submit(); $("#join-form")[0].submit();인지 헷갈  
		$("#join-form")[0].submit();
		
		
	});
	
	
	$("#email").change(function(){
		$('#img-checkemail').hide();
		$('#btn-checkemail').show();
	});
	
	
	$("#btn-checkemail").click(function(){
		var email = $("#email").val();
		if(email == ""){
			return;
		}
		
		
		$.ajax({
			url:"${pageContext.request.contextPath }/user/api/checkemail?email="+ email,
			type:"get",
			dataType:"json",
			success: function(response){
				if(response.result !== 'success'){
					console.error(response.message);
					return;
				}
				
				
				if(response.data){
					//alert("존재하는 이메일입니다. 다른 이메일을 사용해주세요.");
					messageBox("이메일 중복 확인","존재하는 이메일입니다. 다른 이메일을 사용해주세요.",function(){
						$("#email")
						.val('')
						.focus();
					})
					
					return;
				}
				
				$('#img-checkemail').show();
				$('#btn-checkemail').hide();
			},
			error: function(xhr, status, e){
				console.log(status, e);
			}
		});	
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form:form 
					modelAttribute="userVo" 
					id="join-form" 
					name="joinForm" 
					method="post"	
					action="${pageContext.request.contextPath }/user/join">
					
					<label class="block-label" for="name">이름</label>
					<form:input path="name" />
					<p style="text-align:left; padding-left:0; color:#f00">
						<spring:hasBindErrors name="userVo">
							<c:if test="${errors.hasFieldErrors('name') }">
								<spring:message code="${errors.getFieldError('name').codes[0] }" />
							</c:if>
						</spring:hasBindErrors>
					</p>
					
					<label class="block-label" for="email"><spring:message code="user.join.label.email" /></label>
					<form:input path="email" />
					<input type="button" id="btn-checkemail" value="중복체크">
					<img id="img-checkemail" src='${pageContext.request.contextPath }/assets/images/check_icon.png' style="width: 24px; display:none">
					<p style="text-align:left; padding-left:0; color:#f00">
						<form:errors path="email" />
					</p>	
					
					<label class="block-label"><spring:message code="user.join.label.password" /></label>
					<form:password path="password" />
					<p style="text-align:left; padding-left:0; color:#f00">
						<form:errors path="password" />
					</p>	
					
					<fieldset id="gender-field">
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label="여" />
						<form:radiobutton path="gender" value="male" label="남" />
					</fieldset>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input type="submit" value="가입하기">

				</form:form>
			</div>
		</div>
		
		<div id="dialog-message" title="Join" style="display:none; line-height: 50px;">
		  <p></p>
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>