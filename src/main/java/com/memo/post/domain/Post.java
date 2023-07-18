package com.memo.post.domain;

import java.time.ZonedDateTime;

import lombok.Data;

@Data	// getter와 setter가 있다.
public class Post {

	private int id;
	private int userId;
	private String subject;
	private String content;
	private String imagePath;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
}
