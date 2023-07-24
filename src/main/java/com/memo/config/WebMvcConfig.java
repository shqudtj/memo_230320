package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;

@Configuration	// 설정을 위한 spring bean
public class WebMvcConfig implements WebMvcConfigurer {
	
	// 웹 이미지 path와 서버에 업로드 된 이미지와 매핑 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**")		// 웹 이미지 path 예) http://localhost/images/aaa_1644651416//sun.png
		// 윈도우용 /// 3개
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일 위치 맥은 // 2개 윈도우는 /// 3개
		// 맥용 // 2개
		//.addResourceLocations("file://" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일 위치 맥은 // 2개 윈도우는 /// 3개
	}
}
