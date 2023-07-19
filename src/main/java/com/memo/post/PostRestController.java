package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	private PostBO postBO;
	
	
	@PostMapping("/create")
	public Map<String, Object> create(
				@RequestParam("subject") String subject
				, @RequestParam("content") String content
				, @RequestParam(value = "file", required = false) MultipartFile file
				, HttpSession session
			) {
		
		// 세션에서 유저 정보 받아옴
		int userId = (int)session.getAttribute("userId");
		// 많은 이용자가 있는 경우는 integer로 해야하는데 혹시 로그아웃된 사람이 들어올수 잇어서
		// 하지만 지금 프로젝트에서는 총괄체크할거기때문에 그대로 진행
		
		
		// db insert
		postBO.addPost(userId, subject, content, file);
		
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		result.put("result", "성공");
		
		return result;
	}
	
}











