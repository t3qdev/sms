package com.b5m.sms.biz.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.b5m.sms.vo.SmsMsRoleUserVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
public class SmsMsRoleUserDAOTest extends EgovAbstractDAO{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public List<SmsMsRoleUserVO> testSelectSmsMsRoleUser() {
//		@SuppressWarnings("unchecked")
//		public List<SmsMsRoleUserVO> selectSmsMsRoleUser(SmsMsRoleUserVO smsMsRoleUserVO) throws Exception{
		SmsMsRoleUserVO smsMsRoleUserVO = new SmsMsRoleUserVO();
		smsMsRoleUserVO.setUserEml("juyobi@nate.com");
		return  (List<SmsMsRoleUserVO>) list("smsMsRoleUserDAO.selectSmsMsRoleUser",smsMsRoleUserVO);
//		}
//		public void deleteSmsMsRoleUserByUserEml(SmsMsRoleUserVO smsMsRoleUserVO)throws Exception{
//			delete("smsMsRoleUserDAO.deleteSmsMsRoleUserByUserEml",smsMsRoleUserVO);
//		}
//		public void insertSmsMsRoleUser(SmsMsRoleUserVO smsMsRoleUserVO)throws Exception{
//			insert("smsMsRoleUserDAO.insertSmsMsRoleUser",smsMsRoleUserVO);
//		}
	}

	@Test
	public void testDeleteSmsMsRoleUserByUserEml() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertSmsMsRoleUser() {
		fail("Not yet implemented");
	}

}
