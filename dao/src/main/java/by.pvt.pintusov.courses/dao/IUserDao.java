package by.pvt.pintusov.courses.dao;

import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.pojos.User;

/**
 * User dao interface
 * Project: courses
 * Created by: dpintusov
 * Date: 20.01.17.
 */
public interface IUserDao extends IDao<User> {

	/**
	 * Gets user by login
	 * @param login - user's login
	 * @return user
	 * @throws DaoException
	 */
	User getUserByLogin (String login) throws DaoException;
}
