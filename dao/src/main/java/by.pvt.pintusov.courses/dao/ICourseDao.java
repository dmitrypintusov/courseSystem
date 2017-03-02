package by.pvt.pintusov.courses.dao;

import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.pojos.Course;

import java.util.List;

/**
 * Course dao interface
 * Project name: courses
 * Created by dpintusov
 * Date: 22.01.2017.
 */
public interface ICourseDao extends IDao<Course> {

	/**
	 * Check if course status is ended
	 * @param courseName - course name to check
	 * @return true of false depending on result
	 * @throws DaoException
	 */
	boolean isCourseStatusEnded (String courseName) throws DaoException;

	/**
	 * Getting list of course for pagination
	 * @param recordsPerPage - records per page
	 * @param pageNumber - number of pages
	 * @param sorting - sorting type
	 * @return list of course depending on request
	 * @throws DaoException
	 */
	List<Course> getCourses (int recordsPerPage, int pageNumber, String sorting) throws DaoException;

	/**
	 * Getting course by it name
	 * @param courseName - course name
	 * @return entity of course from database
	 * @throws DaoException
	 */
	Course getByCourseName (String courseName) throws DaoException;
}
