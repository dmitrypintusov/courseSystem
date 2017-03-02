package by.pvt.pintusov.courses.services;

import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.User;

/**
 * User service interface
 * Project name: courses
 * Created by dpintusov
 * Date: 20.01.2017.
 */
public interface IUserService extends IService<User> {

	/**
	 * Getting user by login
	 * @param login - user login
	 * @return user entity
	 * @throws ServiceException
	 */
	User getUserByLogin(String login) throws ServiceException;

	/**
	 * Checking if user is new
	 * @param login - user login
	 * @return true or false depending on result
	 * @throws ServiceException
	 */
	boolean checkIsNewUser(String login) throws ServiceException;

	/**
	 * Adding user to database
	 * with access level type - STUDENT
	 * @param user - user entity
	 * @throws ServiceException
	 */
	void bookUser(User user) throws ServiceException;
}
