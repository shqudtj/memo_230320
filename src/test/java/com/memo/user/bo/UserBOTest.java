package com.memo.user.bo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.memo.user.entity.UserEntity;

@SpringBootTest
class UserBOTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserBO userBO;
	
	//@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void 회원조회() {
		UserEntity user = userBO.getUserEntityByLoginId("aaaa");
		logger.info("###### user: {}", user);
		assertNotNull(user);  // notNull인지 확인가능
	}
	
	@Transactional	// rollback 실행은 하고 결과를 알려주지만 db에 저장은 안한다.
	// test에서만이 아닌 실제 bo에서나 아무데서도 쓸 수 있음  ex) 주문을 할 떄 많은 단계를 거치는데 중간에 에러가나도 롤백가능
	//@Test
	void 회원추가테스트() {
		userBO.addUser("testId222", "test222", "testName222", "testEmail222");
	}

}
