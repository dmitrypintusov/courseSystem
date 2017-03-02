package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.dao.IAccessLevelDao;
import by.pvt.pintusov.courses.enums.AccessLevelType;
import by.pvt.pintusov.courses.enums.ServiceConstants;
import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.AccessLevel;
import by.pvt.pintusov.courses.services.AbstractService;
import by.pvt.pintusov.courses.services.IAccessLevelService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Access level implementation
 * with transaction support
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class AccessLevelServiceImpl extends AbstractService<AccessLevel> implements IAccessLevelService {
	private static Logger logger = Logger.getLogger(AccessLevelServiceImpl.class);

	@Autowired
	private IAccessLevelDao accessDao;

	@Autowired
	public AccessLevelServiceImpl (IAccessLevelDao accessDao) {
		super (accessDao);
		this.accessDao = accessDao;
	}

	@Override
	@Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
	public AccessLevel getByAccessLevelType(AccessLevelType accessLevelType) throws ServiceException {
		AccessLevel accessLevel;
		try {
			accessLevel = accessDao.getByAccessLevelType(accessLevelType);
		} catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		}
		return accessLevel;
	}
}
