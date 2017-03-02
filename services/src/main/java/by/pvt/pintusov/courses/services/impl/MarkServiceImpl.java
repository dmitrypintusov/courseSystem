package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.dao.ICourseDao;
import by.pvt.pintusov.courses.dao.IMarkDao;
import by.pvt.pintusov.courses.dao.IUserDao;
import by.pvt.pintusov.courses.enums.ServiceConstants;
import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.Course;
import by.pvt.pintusov.courses.pojos.Mark;
import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.services.AbstractService;
import by.pvt.pintusov.courses.services.IMarkService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Calendar;

/**
 * Mark service implementation
 * with transaction support
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
@Service
@Transactional (propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class MarkServiceImpl extends AbstractService<Mark> implements IMarkService {
	private static Logger logger = Logger.getLogger(MarkServiceImpl.class);

	@Autowired
	private IUserDao userDao;
	@Autowired
	private ICourseDao courseDao;
	@Autowired
	private IMarkDao markDao;

	@Autowired
	public MarkServiceImpl (IMarkDao markDao) {
		super (markDao);
	}

	@Override
	public void addMark(Integer userId, Integer mark, String courseName) throws ServiceException {
		try {
			User user = userDao.getById(userId);
			Course course = courseDao.getByCourseName(courseName);
			Mark studentMark = new Mark();
			studentMark.setMark(mark);
			studentMark.setUser(user);
			studentMark.setCourse(course);
			studentMark.setDate(Calendar.getInstance());
			user.addMark(studentMark);
			userDao.saveOrUpdate(user);
			markDao.saveOrUpdate(studentMark);
		} catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		};
	}
}
