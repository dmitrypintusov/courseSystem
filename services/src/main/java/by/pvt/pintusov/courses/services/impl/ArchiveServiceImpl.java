package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.dao.IArchiveDao;
import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.pojos.Archive;
import by.pvt.pintusov.courses.services.AbstractService;
import by.pvt.pintusov.courses.services.IArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Archive service implementation
 * Project: courses
 * Created by: USER
 * Date: 24.01.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class ArchiveServiceImpl extends AbstractService<Archive> implements IArchiveService {

	@Autowired
	public ArchiveServiceImpl (IArchiveDao archiveDao) {
		super (archiveDao);
	}
}
