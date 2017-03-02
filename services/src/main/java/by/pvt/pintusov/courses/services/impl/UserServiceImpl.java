package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.dao.IAccessLevelDao;
import by.pvt.pintusov.courses.dao.IUserDao;
import by.pvt.pintusov.courses.enums.AccessLevelType;
import by.pvt.pintusov.courses.enums.ServiceConstants;
import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.AccessLevel;
import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.services.AbstractService;
import by.pvt.pintusov.courses.services.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * User service implementation
 *  with transaction support
 * @author dpintusov
 * @version 1.2
 */

@Service
@Transactional (propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class UserServiceImpl extends AbstractService <User> implements IUserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IAccessLevelDao accessLevelDao;

	@Autowired
	public UserServiceImpl (IUserDao userDao) {
		super (userDao);
		this.userDao = userDao;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User getUserByLogin(String login) throws ServiceException {
		User user;
		try {
			user = userDao.getUserByLogin(login);

		}
		catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		}
		return user;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkIsNewUser(String login) throws ServiceException {
		boolean isNew = false;
		try {
			User user = userDao.getUserByLogin(login);
			if (user == null) {
				isNew = true;
			}
		} catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		}
		return isNew;
	}

	@Override
	public void bookUser(User user) throws ServiceException {
		try {
			AccessLevel accessLevel = new AccessLevel();
			accessLevel.setAccessLevelType(AccessLevelType.STUDENT);
			user.addAccessLevel(accessLevel);
			accessLevel.addUser(user);
			userDao.saveOrUpdate(user);
			accessLevelDao.saveOrUpdate(accessLevel);
		} catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		}
	}
}
