package com.memo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

//@SpringBootTest
class MemoApplicationTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void 비어있는지테스트() {
		String a= null;		// ""
		if (ObjectUtils.isEmpty(a)) {
			logger.info("###### is empty.");
		}
		
		List<Integer> list = List.of(1, 2, 3, 4);  // -> java 12버전부터가능
		List<Integer> list1 = List.of();		// null
		if (ObjectUtils.isEmpty(list1)) {
			logger.info("###### list is empty.");
		}
	}
	
	//@Test
	void 더하기테스트() {
		logger.debug("###### 더하기 테스트 시작");
		int a = 10;
		int b = 20;
		
		assertEquals(20, 더하기(a,b));
	}
	
	int 더하기(int x, int y) {
		return x + y;
	}

}
