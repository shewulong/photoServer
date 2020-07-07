package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseDaoTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRegister() {
		BaseDao.init();
		assertEquals(0, BaseDao.register("yuki", "123456qaz"));
		BaseDao.close();
	}

	@Test
	void testUpdateUserInfo() {
		BaseDao.init();
		assertEquals(1, BaseDao.updateUserInfo("0e878240e0ec488a93333b918f1a0398", "mafu", "123456qaz"));
		BaseDao.close();
	}

	@Test
	void testCreateGroup() {
		BaseDao.init();
		assertEquals(1, BaseDao.createGroup("0e878240e0ec488a93333b918f1a0398", "hello"));
		BaseDao.close();
	}

	@Test
	void testDeleteGroup() {
		BaseDao.init();
		assertEquals(0, BaseDao.deleteGroup("0e878240e0ec488a93333b918f1a0398", 1, 0));
		BaseDao.close();
	}

	@Test
	void testUpdateSignature() {
		BaseDao.init();
		assertEquals(1, BaseDao.updateSignature("0e878240e0ec488a93333b918f1a0398", "你好，陌生人"));
		BaseDao.close();
	}

}
