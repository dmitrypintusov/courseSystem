package by.pvt.pintusov.courses.dao.impl;

import by.pvt.pintusov.courses.dao.IAccessLevelDao;
import by.pvt.pintusov.courses.enums.AccessLevelType;
import by.pvt.pintusov.courses.pojos.AccessLevel;
import by.pvt.pintusov.courses.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Project: courses
 * Created by: USER
 * Date: 25.01.17.
 */
@ContextConfiguration("/test-dao-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "testDaoTransactionManager")
public class AccessLevelDaoImplTest {
	private AccessLevel expectedAccessLevel;
	private AccessLevel actualAccessLevel;
	private Serializable accessLevelId;

	@Autowired
	private IAccessLevelDao accessLevelDao;

	@Before
	public void setUp() throws Exception {
		expectedAccessLevel = EntityBuilder.buildAccessLevel(AccessLevelType.ADMIN, null);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualAccessLevel = accessLevelDao.getById((Integer) accessLevelId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedAccessLevel, actualAccessLevel);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualAccessLevel = accessLevelDao.getById((Integer) accessLevelId);
		Assert.assertEquals("getById() method failed: ", expectedAccessLevel, actualAccessLevel);
		delete();
	}

	@Test
	public void testLoad () throws Exception {
		createEntities();
		actualAccessLevel = accessLevelDao.load((Integer) accessLevelId);
		Assert.assertEquals("load() method failed: ", expectedAccessLevel, actualAccessLevel);
		delete();
	}

	@Test
	public void testGetByAccessLevelType () throws Exception {
		createEntities();
		actualAccessLevel = accessLevelDao.getByAccessLevelType(AccessLevelType.ADMIN);
		Assert.assertEquals("load() method failed: ", expectedAccessLevel, actualAccessLevel);
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualAccessLevel = accessLevelDao.getById((Integer) accessLevelId);
		Assert.assertNull("delete() method failed: ", actualAccessLevel);
	}

	@After
	public void tearDown () throws Exception {
		expectedAccessLevel = null;
		actualAccessLevel = null;
		accessLevelId = null;
		accessLevelDao = null;
	}

	private void createEntities() throws Exception {
		accessLevelId = accessLevelDao.saveOrUpdate(expectedAccessLevel);
		expectedAccessLevel.setId((Integer) accessLevelId);
	}

	private void delete() throws Exception {
		accessLevelDao.delete((Integer) accessLevelId);
	}
}
