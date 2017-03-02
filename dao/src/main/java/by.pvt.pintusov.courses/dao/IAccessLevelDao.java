package by.pvt.pintusov.courses.dao;

import by.pvt.pintusov.courses.enums.AccessLevelType;
import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.pojos.AccessLevel;

/**
 * Access level dao interface
 * Project name: courses
 * Created by dpintusov
 * Date: 22.01.2017.
 */
public interface IAccessLevelDao extends IDao <AccessLevel> {

	/**
	 * Getting access level entity by type
	 * @param accessLevelType - access level type
	 * @return entity of access level
	 * @throws DaoException
	 */
	AccessLevel getByAccessLevelType(AccessLevelType accessLevelType) throws DaoException;
}
