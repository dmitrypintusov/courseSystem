package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.pojos.Archive;
import by.pvt.pintusov.courses.services.IArchiveService;
import by.pvt.pintusov.courses.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Testing archive service
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
@Transactional
@ContextConfiguration("/test-services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ArchiveServiceImplTest {

	@Autowired
	private IArchiveService archiveService;

	private Archive expectedArchive;
	private Archive actualAccessLevel;
	private Serializable archiveId;

	@Before
	public void setUp() throws Exception {
		expectedArchive = EntityBuilder.buildArchive(null);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualAccessLevel = archiveService.getById((Integer) archiveId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedArchive, actualAccessLevel);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualAccessLevel = archiveService.getById((Integer) archiveId);
		Assert.assertEquals("getById() method failed: ", expectedArchive, actualAccessLevel);
		delete();
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualAccessLevel = archiveService.getById((Integer) archiveId);
		Assert.assertNull("delete() method failed: ", actualAccessLevel);
	}

	@After
	public void tearDown () throws Exception {
		expectedArchive = null;
		actualAccessLevel = null;
		archiveId = null;
		archiveService = null;
	}

	private void createEntities() throws Exception {
		archiveId = archiveService.saveOrUpdate(expectedArchive);
		expectedArchive.setId((Integer) archiveId);
	}

	private void delete() throws Exception {
		archiveService.delete((Integer) archiveId);
	}
}
