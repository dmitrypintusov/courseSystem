package by.pvt.pintusov.courses.dao.impl;

import by.pvt.pintusov.courses.constants.DaoConstants;
import by.pvt.pintusov.courses.dao.AbstractDao;
import by.pvt.pintusov.courses.dao.IUserDao;
import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.pojos.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * User Dao implementation
 * @author dpintusov
 * @version 1.2
 */
@Repository
public class UserDaoImpl extends AbstractDao <User> implements IUserDao {
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Autowired
	private UserDaoImpl(SessionFactory sessionFactory){
		super(User.class, sessionFactory);
	}

	@Override
	public User getUserByLogin(String login) throws DaoException {
		User user;
		try {
			Session session = getCurrentSession();
			Query query = session.createQuery(DaoConstants.HQL_GET_BY_LOGIN);
			query.setParameter(DaoConstants.PARAMETER_USER_LOGIN, login);
			user = (User) query.uniqueResult();
		} catch(HibernateException e){
			logger.error(DaoConstants.ERROR_USER_BY_LOGIN + e);
			throw new DaoException(DaoConstants.ERROR_USER_BY_LOGIN, e);
		}
		return user;
	}
}
