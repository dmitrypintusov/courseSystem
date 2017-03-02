package by.pvt.pintusov.courses.dao.impl;

import by.pvt.pintusov.courses.dao.AbstractDao;
import by.pvt.pintusov.courses.dao.IMarkDao;
import by.pvt.pintusov.courses.pojos.Mark;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Mark dao implementation
 * Project: courses
 * Created by: dpintusov
 * Date: 23.01.17.
 */
@Repository
public class MarkDaoImpl extends AbstractDao<Mark> implements IMarkDao {
	private static Logger logger = Logger.getLogger(MarkDaoImpl.class);

	@Autowired
	private MarkDaoImpl(SessionFactory sessionFactory){
		super(Mark.class, sessionFactory);
	}
}
