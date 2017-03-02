package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.enums.CourseStatusType;
import by.pvt.pintusov.courses.pojos.Course;
import by.pvt.pintusov.courses.services.ICourseService;
import by.pvt.pintusov.courses.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Testing course service
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
@Transactional
@ContextConfiguration("/test-services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseServiceImplTest {

	@Autowired
	private ICourseService courseService;

	private Course expectedCourse;
	private Course actualCourse;
	private Serializable courseId;

	@Before
	public void setUp() throws Exception {
		expectedCourse = EntityBuilder.buildCourse("TEST", 100, CourseStatusType.OPEN, null, null, null);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualCourse = courseService.getById((Integer) courseId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedCourse, actualCourse);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualCourse = courseService.getById((Integer) courseId);
		Assert.assertEquals("getById() method failed: ", expectedCourse, actualCourse);
		delete();
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualCourse = courseService.getById((Integer) courseId);
		Assert.assertNull("delete() method failed: ", actualCourse);
	}

	@Test
	public void testGetByCourseName () throws Exception {
		createEntities();
		actualCourse = courseService.getByCourseName(expectedCourse.getCourseName());
		Assert.assertEquals("getByCourseName() method failed: ", expectedCourse, actualCourse);
		delete();
	}

	@Test
	public void testIsCourseStatusEnded () throws Exception {
		createEntities();
		boolean isEnded;
		isEnded = courseService.isCourseStatusEnded(expectedCourse.getCourseName());
		Assert.assertFalse("isCourseStatusEnded() method failed: ", isEnded);
		delete();
	}

	@After
	public void tearDown () throws Exception {
		expectedCourse = null;
		actualCourse = null;
		courseId = null;
		courseService = null;
	}

	private void createEntities() throws Exception {
		courseId = courseService.saveOrUpdate(expectedCourse);
		expectedCourse.setId((Integer) courseId);
	}

	private void delete() throws Exception {
		courseService.delete((Integer) courseId);
	}
}
