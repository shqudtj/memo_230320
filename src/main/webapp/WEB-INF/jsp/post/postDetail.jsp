<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글상세</h1>

		<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요." value="${post.subject}">
		<textarea id="content" class="form-control" rows="10" placeholder="내용을 입력하세요.">${post.content}</textarea>
		
		<%-- 이미지가 있을 때만 이미지 영역 추가 --%>
		<c:if test="${not empty post.imagePath}">
			<div class="my-3">
				<img src="${post.imagePath}" alt="업로드 된 이미지" width="300">
			</div>
		</c:if>
		
		<div class="d-flex justify-content-end my-4">
			<input type="file" id="file" accept=".jpg, .jpeg, .png, .gif">
		</div>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="deleteBtn" class="btn btn-secondary" data-post-id="${post.id}">삭제</button>
			<div class="d-flex px-3">
				<a href="/post/post_list_view" type="button" id="postListBtn" class="btn btn-dark">목록</a>
				<button type="button" id="updateBtn" class="btn btn-warning" data-post-id="${post.id}">수정</button>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		
		// 목록 버튼 클릭 => 글 목록 이동
		$('#postListBtn').on("click", function() {
			location.href="/post/post_list_view";
		});
		
		// 수정 버튼 클릭
		$('#updateBtn').on('click', function() {
			//alert('수정버튼클릭');
			
			let subject = $('#subject').val().trim();
			let content = $('#content').val();
			let file = $('#file').val();
			
			if (!subject) {
				alert("제목을 입력하세요");
				return;
			}
			if (!content) {
				alert("내용을 입력하세요");
				return;
			}
			console.log(file);	// C:\fakepath\hallstatt-3609863_1280.jpg
			
			// 파일이 업로드 된 경우 확장자 체크
			if (file) {
				let ext = file.split('.').pop().toLowerCase(); // pop -> 배열의 마지막  / toLowerCase -> 소문자변경
				//alert(ext);
				
				if ($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) == -1 ) { // -1 -> 배열에 없다는 뜻
					alert('이미지 파일만 업로드 할 수 있습니다.');
					$("#file").val("");	// 파일을 비운다.
					return;
				}
			}
			
			
			// form 태그를 스크립트에서 만든다.
			let postId = $(this).data('post-id');
			// alert(postId);
			let formData = new FormData();
			formData.append("postId", postId);
			formData.append("subject", subject);
			formData.append("content", content);
			formData.append("file", $('#file')[0].files[0]);
			
			
			// ajax
			$.ajax({
				// request
				type:"put"
				, url:"/post/update"
				, data:formData
				, enctype:"multipart/form-data" // 파일 업로드를 위한 필수 설정
				, processData:false // 파일 업로드를 위한 필수 설정
				, contentType:false // 파일 업로드를 위한 필수 설정
				
				
				// response
				, success:function(data) {
					if (data.code == 1) {
						alert("메모가 수정되었습니다.");
						location.reload(true);
					} else {
						alert(data.errorMessage);
					}
				}
				, error:function(request, status, error) {
					alert("메모 수정 실패했습니다.");
				}
			});
		});
		
		// 삭제
		$("#deleteBtn").on('click', function() {
			//alert("삭제버튼");
			let postId = $(this).data("post-id");
			//alert(postId);
			
			$.ajax({
				//request
				type:"delete"
				, url:"/post/delete"
				, data:{"postId":postId}
			
				// response
				, success:function(data) {
					if (data.code == 1) {
						alert("삭제되었습니다.");
						location.href="/post/post_list_view";
					} else {
						alert(data.errorMessage);
					}
				}
				, error:function(request, status, error) {
					alert("메모를 삭제하는데 실패했습니다.");
				}
			});
		});
		
		
	});
</script>



