<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div>
		<form id="signUpForm" method="post" action="/user/sign_up">
			<table class="table sign-up-table table-bordered">
				<tr>
					<th>아이디</th>
					<td>
						<div class="d-flex">
							<input id="loginId" name="loginId" type="text" class="form-control">
							<button id="loginIdCheckBtn"  type="button" class="btn bg-info text-white">중복확인</button>
						</div>
						
						<%-- 아이디 체크 결과 --%>
						<%-- d-none 클래스: display none (보이지 않게) --%>
						<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
						<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
						<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input  id="password" name="password" type="password" class="form-control"></td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td><input id="confirmPassword" type="password" class="form-control"></td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input id="name" name="name" type="text" class="form-control" placeholder="이름을 입력하세요"></td>
				</tr>
				<tr>
					<th>이메일 주소</th>
					<td><input id="email" name="email" type="text" class="form-control" placeholder="이메일 주소를 입력하세요"></td>
				</tr>
			</table>
			
			<button type="submit" class="btn bg-primary text-white">회원가입</button>
		</form>
	</div>
</div>		

<script>
	$(document).ready(function() {
		// 중복확인 버튼 클릭
		$("#loginIdCheckBtn").on('click', function() {
			// alert("클릭");
			
			// 경고 문구 초기화
			$('#idCheckLength').addClass('d-none');
			$('#idCheckDuplicated').addClass('d-none');
			$('#idCheckOk').addClass('d-none');
			
			let loginId = $('#loginId').val().trim();
			if (loginId.length < 4) {
				$('#idCheckLength').removeClass('d-none');
				return;
			}
			
			// AJAX 통신 - 중복확인
			$.ajax({
				// request
				url:"/user/is_duplicated_id"
				, data: {"loginId":loginId}
				
				// response
				, success: function(data) {
					if (data.isDuplicatedId) {
						// 중복
						$('#idCheckDuplicated').removeClass('d-none');
					} else {
						// 중복아님 => 사용 가능
						$('#idCheckOk').removeClass('d-none');
					}			
				}
				, error: function(request, status, error) {
					alert('중복확인에 실패했습니다.');
				}
				
				
			});
			
		});
	});
	
</script>