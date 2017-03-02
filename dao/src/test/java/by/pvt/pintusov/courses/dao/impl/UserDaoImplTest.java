package by.pvt.pintusov.courses.dao.impl;

import by.pvt.pintusov.courses.dao.IAccessLevelDao;
import by.pvt.pintusov.courses.dao.ICourseDao;
import by.pvt.pintusov.courses.dao.IMarkDao;
import by.pvt.pintusov.courses.dao.IUserDao;
import by.pvt.pintusov.courses.enums.AccessLevelType;
import by.pvt.pintusov.courses.enums.CourseStatusType;
import by.pvt.pintusov.courses.pojos.AccessLevel;
import by.pvt.pintusov.courses.pojos.Course;
import by.pvt.pintusov.courses.pojos.Mark;
import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * Test User Dao
 * @author dpintusov
 * @version 1.2
 */

@ContextConfiguration ("/test-dao-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "testDaoTransactionManager")
public class UserDaoImplTest {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private ICourseDao courseDao;
	@Autowired
	private IMarkDao markDao;
	@Autowired
	private IAccessLevelDao accessLevelDao;

	private User expectedUser;
	private User actualUser;
	private Course course;
	private Mark mark;
	private AccessLevel accessLevel;
	private Serializable userId;
	private Serializable courseId;
	private Serializable markId;
	private Serializable accessLevelId;

	private Set<User> users;
	private Set<Course> courses;
	private Set<AccessLevel> accessLevels;
	private Set<Mark> marks;

	@Before
	public void setUp() throws Exception {
		courses = new HashSet<>();
		accessLevels = new HashSet<>();
		marks = new HashSet<>();
		users = new HashSet<>();

		expectedUser = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", null, null, null);
		users.add(expectedUser);

		course = EntityBuilder.buildCourse("COURSE", 100, CourseStatusType.OPEN, Calendar.getInstance(), Calendar.getInstance(), users);
		courses.add(course);
		expectedUser.setCourses(courses);

		mark = EntityBuilder.buildMark(10, expectedUser, course, Calendar.getInstance());
		marks.add(mark);
		expectedUser.setMarks(marks);

		accessLevel = EntityBuilder.buildAccessLevel(AccessLevelType.STUDENT, users);
		accessLevels.add(accessLevel);
		expectedUser.setAccessLevels(accessLevels);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualUser = userDao.getById((Integer) userId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedUser, actualUser);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualUser = userDao.getById((Integer) userId);
		Assert.assertEquals("getById() method failed: ", expectedUser, actualUser);
		delete();
	}

	@Test
	public void testLoad () throws Exception {
		createEntities();
		actualUser = userDao.load((Integer) userId);
		Assert.assertEquals("load() method failed: ", expectedUser, actualUser);
		delete();
	}

	@Test
	public void testGetByLogin () throws Exception {
		createEntities();
		actualUser = userDao.getUserByLogin(expectedUser.getLogin());
		Assert.assertEquals("getByLogin() method failed: ", expectedUser, actualUser);
		delete();
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualUser = userDao.getById((Integer) userId);
		Assert.assertNull("delete() method failed: ", actualUser);
	}

	@After
	public void tearDown () throws Exception {
		expectedUser = null;
		actualUser = null;
		course = null;
		mark = null;
		accessLevel = null;
		userId = null;
		courseId = null;
		markId = null;
		accessLevelId = null;
	}

	private void createEntities() throws Exception {
		accessLevelId = accessLevelDao.saveOrUpdate(accessLevel);
		markId = markDao.saveOrUpdate(mark);
		userId = userDao.saveOrUpdate(expectedUser);
		courseId = courseDao.saveOrUpdate(course);
		expectedUser.setId((Integer) userId);
	}

	private void delete() throws Exception {
		accessLevelDao.delete((Integer) accessLevelId);
		markDao.delete((Integer) markId);
		userDao.delete((Integer) userId);
	}
}
