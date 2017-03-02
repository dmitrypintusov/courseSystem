package by.pvt.pintusov.courses.services;

import by.pvt.pintusov.courses.enums.AccessLevelType;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.AccessLevel;

/**
 * Access level interface
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
public interface IAccessLevelService extends IService<AccessLevel> {

	/**
	 * Getting access level
	 * by access level type
	 * @param accessLevelType - access level type
	 * @return access level entity
	 * @throws ServiceException
	 */
	AccessLevel getByAccessLevelType(AccessLevelType accessLevelType) throws ServiceException;
}
