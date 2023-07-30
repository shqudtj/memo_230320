package com.memo.post.bo;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostMapper;
import com.memo.post.domain.Post;

@Service
public class PostBO {
	private Logger logger = LoggerFactory.getLogger(this.getClass()); // import가 자동으로 mybatis꺼로 되는데 지우고 slf4j를 import
	//private Logger logger = LoggerFactory.getLogger(PostBO.class); // import가 자동으로 mybatis꺼로 되는데 지우고 slf4j를 import
	
	private static final int POST_MAX_SIZE = 3;
	
	@Autowired
	private PostMapper postMapper;	// mybatis
	
	@Autowired
	private FileManagerService fileManager;
	
	// 로그인된 상태의 list를 조회해야함
	// input: userId(글쓴이)
	// output: List<Post>
	public List<Post> getPostListByUserId(int userId, Integer prevId, Integer nextId) {
		// 게시글 번호: 10 9 8 | 7 6 5 | 4 3 2 | 1
		// 만약 4 3 2 페이지에 있을 때
		// 1) 다음: 2 보다 작은 3개 DESC
		// 2) 이전: 4 보다 큰 3개 ASC(5 6 7) => List reverse(7 6 5)
		// 3) 첫페이지일 때(이전, 다음 없음) DESC 3개
		
		String direction = null; // 방향
		Integer standardId = null; // 기준 postId
		if (prevId != null) {
			// 이전
			direction = "prev";
			standardId = prevId;
			
			// get list
			List<Post> postList = postMapper.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
			
			// reverse
			// 5 6 7 => 7 6 5
			Collections.reverse(postList); // 뒤집고 저장까지 해준다 => 마우스를 가져다 대면 void로 리턴되기때문
			
			// return
			return postList; // method 종료
			
		} else if (nextId != null) {
			// 다음
			direction = "next";
			standardId = nextId;
		}
		
		return postMapper.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
	}
	
	// 이전 방향의 끝인지 확인
	// input: prevId, userId
	// output: boolean
	public boolean isPrevLastPage(int prevId, int userId) {
		int postId = postMapper.selectPostIdByUserIdAndSort(userId, "DESC");
		return postId == prevId;	// 같으면 끝, 아니면 끝 아님
	}
	
	// 다음 방향의 끝인지 확인
	public boolean isNextLastPage(int nextId, int userId) {
		return nextId == postMapper.selectPostIdByUserIdAndSort(userId, "ASC");
	}

	public int addPost(int userId, String userLoginId,String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		
		// 이미지가 있으면 업로드 후 imagePath 받아옴
		if (file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		return postMapper.insertPost(userId, subject, content, imagePath);
	}
	
	public Post getPostByPostIdAndUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdAndUserId(postId, userId);
	}
	
	public void updatePost(int userId, String userLoginId, int postId, String subject, String content, MultipartFile file) {
		
		// 업데이트 대상인 기존 글을 가져와본다. (validation, 이미지 교체시 기존 이미지 제거를 위해)
		Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
		if (post == null) {
			logger.warn("###[글 수정] post is null. postId:{}, userId:{}", postId, userId); // warn 이나 ()안의 내용은 개발자 원하는대로하면됨
			return;
		}
		
		// file이 비어있지 않다면 업로드 후 imagePath 얻어옴
		// 업로드가 성공하면 기존 이미지 제거
		String imagePath = null;
		if (file != null) {
			// 새로운 파일에 대한 업로드
			imagePath = fileManager.saveFile(userLoginId, file);
			
			// 기존 이미지 제거
			// 업로드가 성공 했고, 기존 이미지가 존재하는 경우
			if (imagePath != null && post.getImagePath() != null) {
				// 이미지 제거
				fileManager.deleteFile(post.getImagePath());  // imagePath는 수정한 이미지이니까 post.getImagePath()를 입력
			}
		}
		
		// 글 업데이트
		postMapper.updatePostByPostIdAndUserId(postId, userId, subject, content, imagePath);
	}
	
	public void deletePostByPostIdAndUserId(int postId, int userId) {
		// 기존 글을 가져온다. (이미지가 있으면 삭제해야하기 때
		Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
		if (post == null) {
			logger.error("###[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		
		// 기존에 이미지가 있으면 삭제
		if (post.getImagePath() != null) {
			fileManager.deleteFile(post.getImagePath());
		}
		
		postMapper.deletePostByPostIdAndUserId(postId, userId);
	}
	
}





