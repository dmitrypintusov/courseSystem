package by.pvt.pintusov.courses.services.impl;

import by.pvt.pintusov.courses.dao.ICourseDao;
import by.pvt.pintusov.courses.enums.CourseStatusType;
import by.pvt.pintusov.courses.enums.ServiceConstants;
import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.Course;
import by.pvt.pintusov.courses.services.AbstractService;
import by.pvt.pintusov.courses.services.ICourseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Calendar;
import java.util.List;

/**
 * Course service implementation
 * with transaction support
 * Project: courses
 * Created by: dpintusov
 * Date: 24.01.17.
 */
@Service
@Transactional (propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class CourseServiceImpl extends AbstractService<Course> implements ICourseService {
	private static Logger logger = Logger.getLogger(CourseServiceImpl.class);

	@Autowired
	private ICourseDao courseDao;

	@Autowired
	public CourseServiceImpl (ICourseDao courseDao) {
		super (courseDao);
		this.courseDao = courseDao;
	}

	@Override
	@Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
	public int getNumberOfPages(int recordsPerPage) throws ServiceException{
		int numberOfPages;
		try {
			Long numberOfRecords = courseDao.getNumberRecords();
			numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
		}
		catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		}
		return numberOfPages;
	}

	@Override
	@Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Course> getAllToPage(int recordsPerPage, int pageNumber, String sorting) throws ServiceException {
		List<Course> results;
		try {
			results = courseDao.getCourses(recordsPerPage, pageNumber, sorting);
		}
		catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		}
		return results;
	}

	@Override
	public void updateCourseStatus (String courseName, CourseStatusType courseStatus) throws ServiceException {
		try {
			Course course = courseDao.getByCourseName(courseName);
			course.setCourseStatus(courseStatus);
			courseDao.saveOrUpdate(course);
		} catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		}
	}

	@Override
	public void startCourse(Course course) throws ServiceException {
		try {
			CourseStatusType courseStatus = CourseStatusType.OPEN;
			course.setCourseStatus(courseStatus);
			course.setStartDate(Calendar.getInstance());
			courseDao.saveOrUpdate(course);
		} catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		};
	}

	@Override
	@Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
	public Course getByCourseName(String courseName) throws ServiceException {
		Course course;
		try {
			course = courseDao.getByCourseName(courseName);
		} catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		}
		return course;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isCourseStatusEnded(String courseName) throws ServiceException {
		boolean isEnded;
		try {
			isEnded = courseDao.isCourseStatusEnded(courseName);
		} catch (DaoException e) {
			logger.error(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ServiceException(ServiceConstants.TRANSACTION_FAILED, e);
		}
		return isEnded;
	}
}
