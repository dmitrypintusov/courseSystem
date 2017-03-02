package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.enums.AccessLevelType;
import by.pvt.pintusov.courses.pojos.AccessLevel;
import by.pvt.pintusov.courses.services.IAccessLevelService;
import by.pvt.pintusov.courses.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Testing access level
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
@Transactional
@ContextConfiguration("/test-services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AccessLevelServiceImplTest {

	@Autowired
	private IAccessLevelService accessLevelService;

	private AccessLevel expectedAccessLevel;
	private AccessLevel actualAccessLevel;
	private Serializable accessLevelId;

	@Before
	public void setUp() throws Exception {
		expectedAccessLevel = EntityBuilder.buildAccessLevel(AccessLevelType.ADMIN, null);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualAccessLevel = accessLevelService.getById((Integer) accessLevelId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedAccessLevel, actualAccessLevel);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualAccessLevel = accessLevelService.getById((Integer) accessLevelId);
		Assert.assertEquals("getById() method failed: ", expectedAccessLevel, actualAccessLevel);
		delete();
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualAccessLevel = accessLevelService.getById((Integer) accessLevelId);
		Assert.assertNull("delete() method failed: ", actualAccessLevel);
	}

	@Test
	public void testGetByAccessLevelType () throws Exception {
		createEntities();
		actualAccessLevel = accessLevelService.getByAccessLevelType(AccessLevelType.ADMIN);
		Assert.assertEquals("getByAccessLevelType() method failed: ", expectedAccessLevel, actualAccessLevel);
		delete();
	}

	@After
	public void tearDown () throws Exception {
		expectedAccessLevel = null;
		actualAccessLevel = null;
		accessLevelId = null;
		accessLevelService = null;
	}

	private void createEntities() throws Exception {
		accessLevelId = accessLevelService.saveOrUpdate(expectedAccessLevel);
		expectedAccessLevel.setId((Integer) accessLevelId);
	}

	private void delete() throws Exception {
		accessLevelService.delete((Integer) accessLevelId);
	}
}
