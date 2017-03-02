package by.pvt.pintusov.courses.dao.impl;

import by.pvt.pintusov.courses.dao.IMarkDao;
import by.pvt.pintusov.courses.pojos.Mark;
import by.pvt.pintusov.courses.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Project: courses
 * Created by: dpintusov
 * Date: 25.01.17.
 */
@ContextConfiguration("/test-dao-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "testDaoTransactionManager")
public class MarkDaoImplTest {
	private Mark expectedMark;
	private Mark actualMark;
	private Serializable markId;

	@Autowired
	private IMarkDao markDao;

	@Before
	public void setUp() throws Exception {
		expectedMark = EntityBuilder.buildMark(100, null, null, Calendar.getInstance());
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualMark = markDao.getById((Integer) markId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedMark, actualMark);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualMark = markDao.getById((Integer) markId);
		Assert.assertEquals("getById() method failed: ", expectedMark, actualMark);
		delete();
	}

	@Test
	public void testLoad () throws Exception {
		createEntities();
		actualMark = markDao.load((Integer) markId);
		Assert.assertEquals("load() method failed: ", expectedMark, actualMark);
		delete();
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualMark = markDao.getById((Integer) markId);
		Assert.assertNull("delete() method failed: ", actualMark);
	}

	@After
	public void tearDown () throws Exception {
		expectedMark = null;
		actualMark = null;
		markId = null;
		markDao = null;
	}

	private void createEntities() throws Exception {
		markId = markDao.saveOrUpdate(expectedMark);
		expectedMark.setId((Integer) markId);
	}

	private void delete() throws Exception {
		markDao.delete((Integer) markId);
	}
}
