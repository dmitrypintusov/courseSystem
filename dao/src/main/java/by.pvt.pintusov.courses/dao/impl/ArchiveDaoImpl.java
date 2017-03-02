package by.pvt.pintusov.courses.dao.impl;

import by.pvt.pintusov.courses.dao.AbstractDao;
import by.pvt.pintusov.courses.dao.IArchiveDao;
import by.pvt.pintusov.courses.pojos.Archive;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Archive dao implementation
 * Project: courses
 * Created by: dpintusov
 * Date: 23.01.17.
 */
@Repository
public class ArchiveDaoImpl extends AbstractDao<Archive> implements IArchiveDao {
	private static Logger logger = Logger.getLogger(ArchiveDaoImpl.class);

	@Autowired
	private ArchiveDaoImpl(SessionFactory sessionFactory){
		super(Archive.class, sessionFactory);
	}
}
