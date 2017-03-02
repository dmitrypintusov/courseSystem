package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.pojos.Mark;
import by.pvt.pintusov.courses.services.IMarkService;
import by.pvt.pintusov.courses.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Testing mark service
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
@Transactional
@ContextConfiguration("/test-services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MarkServiceImplTest {

	@Autowired
	private IMarkService markService;

	private Mark expectedMark;
	private Mark actualMark;
	private Serializable markId;

	@Before
	public void setUp() throws Exception {
		expectedMark = EntityBuilder.buildMark(10, null, null, null);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualMark = markService.getById((Integer) markId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedMark, actualMark);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualMark = markService.getById((Integer) markId);
		Assert.assertEquals("getById() method failed: ", expectedMark, actualMark);
		delete();
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualMark = markService.getById((Integer) markId);
		Assert.assertNull("delete() method failed: ", actualMark);
	}

	@After
	public void tearDown () throws Exception {
		expectedMark = null;
		actualMark = null;
		markId = null;
		markService = null;
	}

	private void createEntities() throws Exception {
		markId = markService.saveOrUpdate(expectedMark);
		expectedMark.setId((Integer) markId);
	}

	private void delete() throws Exception {
		markService.delete((Integer) markId);
	}
}
