package by.pvt.pintusov.courses.services;

import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.Mark;

/**
 * Mark service interface
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
public interface IMarkService extends IService<Mark> {

	/**
	 * Adding mark to database
	 * using user entity, service entity
	 * @param userId - user id who gets mark
	 * @param mark - mark for user
	 * @param courseName - name of course
	 * @throws ServiceException
	 */
	void addMark (Integer userId, Integer mark, String courseName) throws ServiceException;
}
