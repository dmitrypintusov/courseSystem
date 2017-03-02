package by.pvt.pintusov.courses.dao.impl;

import by.pvt.pintusov.courses.dao.IArchiveDao;
import by.pvt.pintusov.courses.pojos.Archive;
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
 * Created by: dpintusov
 * Date: 25.01.17.
 */
@ContextConfiguration("/test-dao-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "testDaoTransactionManager")
public class ArchiveDaoImplTest {
	private Archive expectedArchive;
	private Archive actualArchive;
	private Serializable archiveId;

	@Autowired
	private IArchiveDao archiveDao;

	@Before
	public void setUp() throws Exception {
		expectedArchive = EntityBuilder.buildArchive(null);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		createEntities();
		actualArchive = archiveDao.getById((Integer) archiveId);
		Assert.assertEquals("saveOrUpdate() method failed: ", expectedArchive, actualArchive);
		delete();
	}

	@Test
	public void testGetById() throws Exception {
		createEntities();
		actualArchive = archiveDao.getById((Integer) archiveId);
		Assert.assertEquals("getById() method failed: ", expectedArchive, actualArchive);
		delete();
	}

	@Test
	public void testLoad () throws Exception {
		createEntities();
		actualArchive = archiveDao.load((Integer) archiveId);
		Assert.assertEquals("load() method failed: ", expectedArchive, actualArchive);
		delete();
	}

	@Test
	public void testDelete () throws Exception {
		createEntities();
		delete();
		actualArchive = archiveDao.getById((Integer) archiveId);
		Assert.assertNull("delete() method failed: ", actualArchive);
	}

	@After
	public void tearDown () throws Exception {
		expectedArchive = null;
		actualArchive = null;
		archiveId = null;
		archiveDao = null;
	}

	private void createEntities() throws Exception {
		archiveId = archiveDao.saveOrUpdate(expectedArchive);
		expectedArchive.setId((Integer) archiveId);
	}

	private void delete() throws Exception {
		archiveDao.delete((Integer) archiveId);
	}
}
