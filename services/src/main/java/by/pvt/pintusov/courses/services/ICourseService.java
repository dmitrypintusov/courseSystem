package by.pvt.pintusov.courses.services;

import by.pvt.pintusov.courses.enums.CourseStatusType;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.Course;

import java.util.List;

/**
 * Course service interface
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
public interface ICourseService extends IService<Course> {

	/**
	 * Getting records per page
	 * @param recordsPerPage - number of records
	 * @return number of records
	 * @throws ServiceException
	 */
	int getNumberOfPages(int recordsPerPage) throws ServiceException;

	/**
	 * Getting list of courses for pagination
	 * @param recordsPerPage - records per page
	 * @param pageNumber - number of pages
	 * @param sorting - data sorting
	 * @return list of course depending on params
	 * @throws ServiceException
	 */
	List<Course> getAllToPage(int recordsPerPage, int pageNumber, String sorting) throws ServiceException;

	/**
	 * Updating course status
	 * @param courseName - course name to change
	 * @param courseStatus - course status
	 * @throws ServiceException
	 */
	public void updateCourseStatus (String courseName, CourseStatusType courseStatus) throws ServiceException;

	/**
	 * Starting course
	 * adding status OPEN and start date
	 * @param course - course entity
	 * @throws ServiceException
	 */
	void startCourse (Course course) throws ServiceException;

	/**
	 * Getting course by course name
	 * @param courseName - course name
	 * @return course entity
	 * @throws ServiceException
	 */
	Course getByCourseName (String courseName) throws ServiceException;

	/**
	 * Check if course status is ended
	 * @param courseName - course name
	 * @return true of false depending on result
	 * @throws ServiceException
	 */
	boolean isCourseStatusEnded (String courseName) throws ServiceException;
}
