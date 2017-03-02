package by.pvt.pintusov.courses.dao.impl;

import by.pvt.pintusov.courses.dao.ICourseDao;
import by.pvt.pintusov.courses.enums.CourseStatusType;
import by.pvt.pintusov.courses.pojos.Course;
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
import java.util.Calendar;
import java.util.List;

/**
 * Project: courses
 * Created by: dpintusov
 * Date: 25.01.17.
 */
@ContextConfiguration("/test-dao-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "testDaoTransactionManager")
public class CourseDaoImplTest {
	private Course expectedCourse;
	private Course actualCourse;
	private Serializable courseId;
	private int recordsPerPage;
	private int pageNumber;
	private String sorting;

	@Autowired
	private ICourseDao courseDao;

	@Before
	public void setUp() throws Exception {
		expectedCourse = EntityBuilder.buildCourse("TEST", 100, CourseStatusType.OPEN, Calendar.getInstance(), Calendar.getInstance(), null);
		recordsPerPage = 3;
		pageNumber = 1;
		sorting = "";
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualCourse = courseDao.getById((Integer) courseId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedCourse, actualCourse);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualCourse = courseDao.getById((Integer) courseId);
		Assert.assertEquals("getById() method failed: ", expectedCourse, actualCourse);
		delete();
	}

	@Test
	public void testLoad () throws Exception {
		createEntities();
		actualCourse = courseDao.load((Integer) courseId);
		Assert.assertEquals("load() method failed: ", expectedCourse, actualCourse);
		delete();
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualCourse = courseDao.getById((Integer) courseId);
		Assert.assertNull("delete() method failed: ", actualCourse);
	}

	@Test
	public void testIsCourseStatusEnded () throws Exception {
		createEntities();
		boolean isCourseStatusEnded;
		isCourseStatusEnded = courseDao.isCourseStatusEnded(expectedCourse.getCourseName());
		Assert.assertFalse("isCourseStatusEnded() method failed: ", isCourseStatusEnded);
	}

	@Test
	public void testGetCourses () throws Exception {
		createEntities();
		List<Course> list = courseDao.getCourses(recordsPerPage,pageNumber, "");
		Assert.assertNotNull(list);
		delete();
	}

	@Test
	public void testGetByCourseName () throws Exception {
		createEntities();
		actualCourse = courseDao.getByCourseName(expectedCourse.getCourseName());
		Assert.assertEquals("getbyCourseName() method failed: ", expectedCourse, actualCourse);
		delete();
	}

	@After
	public void tearDown () throws Exception {
		expectedCourse = null;
		actualCourse = null;
		courseId = null;
		courseDao = null;
	}

	private void createEntities() throws Exception {
		courseId = courseDao.saveOrUpdate(expectedCourse);
		expectedCourse.setId((Integer) courseId);
	}

	private void delete() throws Exception {
		courseDao.delete((Integer) courseId);
	}
}
