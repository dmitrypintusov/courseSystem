package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.services.IUserService;
import by.pvt.pintusov.courses.utils.EntityBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Testing user service
 * Created by dpintusov on 09.01.17.
 */
@Transactional
@ContextConfiguration ("/test-services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

	@Autowired
	private IUserService userService;

	private User expectedUser;
	private User actualUser;
	private Serializable userId;

	@Before
	public void setUp() throws Exception {
		expectedUser = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", null, null, null);

	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualUser = userService.getById((Integer) userId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedUser, actualUser);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualUser = userService.getById((Integer) userId);
		Assert.assertEquals("getById() method failed: ", expectedUser, actualUser);
		delete();
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualUser = userService.getById((Integer) userId);
		Assert.assertNull("delete() method failed: ", actualUser);
	}

	@Test
	public void testGetUserByLogin () throws  Exception {
		createEntities();
		actualUser = userService.getUserByLogin(expectedUser.getLogin());
		Assert.assertEquals("getUserByLogin() method failed: ", expectedUser, actualUser);
		delete();
	}

	@Test
	public void testCheckIsUserNew () throws Exception {
		createEntities();
		boolean isNew;
		isNew = userService.checkIsNewUser(expectedUser.getLogin());
		Assert.assertFalse("checkIsUserNew() method failed: ", isNew);
	}

	@After
	public void tearDown () throws Exception {
		expectedUser = null;
		actualUser = null;
		userId = null;
		userService = null;
	}

	private void createEntities() throws Exception {
		userId = userService.saveOrUpdate(expectedUser);
		expectedUser.setId((Integer) userId);
	}

	private void delete() throws Exception {
		userService.delete((Integer) userId);
	}
}
