package by.pvt.pintusov.courses.dao.impl;

import by.pvt.pintusov.courses.constants.DaoConstants;
import by.pvt.pintusov.courses.dao.AbstractDao;
import by.pvt.pintusov.courses.dao.ICourseDao;
import by.pvt.pintusov.courses.enums.CourseStatusType;
import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.pojos.Course;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Course dao implementation
 * Project name: courses
 * Created by dpintusov
 * Date: 22.01.2017.
 */
@Repository
public class CourseDaoImpl extends AbstractDao <Course> implements ICourseDao {
	private static Logger logger = Logger.getLogger(CourseDaoImpl.class);
	private Class persistentClass = Course.class;

	@Autowired
	private CourseDaoImpl(SessionFactory sessionFactory){
		super(Course.class, sessionFactory);
	}

	@Override
	public boolean isCourseStatusEnded(String courseName) throws DaoException {
		boolean isEnded = false;
		try {
			Session session = getCurrentSession();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(Restrictions.eq(DaoConstants.PARAMETER_COURSE_NAME, courseName));
			criteria.add(Restrictions.eq(DaoConstants.PARAMETER_COURSE_STATUS, CourseStatusType.ENDED));
			if (criteria.uniqueResult() != null) {
				isEnded = true;
			}
		} catch(HibernateException e){
			logger.error(DaoConstants.ERROR_COURSE_STATUS + e);
			throw new DaoException(DaoConstants.ERROR_COURSE_STATUS, e);
		}
		return isEnded;
	}

	@Override
	public List<Course> getCourses(int recordsPerPage, int pageNumber, String sorting) throws DaoException {
		List<Course> list;
		try {
			Session session = getCurrentSession();
			Query query = session.createQuery(DaoConstants.HQL_GET_COURSES + sorting);
			query.setFirstResult((pageNumber - 1) * recordsPerPage);
			query.setMaxResults(recordsPerPage);
			list = query.list();
		} catch (HibernateException e) {
			logger.error(DaoConstants.ERROR_COURSES_LIST + e);
			throw new DaoException(DaoConstants.ERROR_COURSES_LIST, e);
		}
		return list;
	}

	@Override
	public Course getByCourseName (String courseName) throws DaoException {
		Course course;
		try {
			Session session = getCurrentSession();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(Restrictions.eq(DaoConstants.PARAMETER_COURSE_NAME, courseName));
			course = (Course) criteria.uniqueResult();
		} catch (HibernateException e) {
			logger.error(DaoConstants.ERROR_COURSE_NAME + e);
			throw new DaoException(DaoConstants.ERROR_COURSE_NAME, e);
		}
		return course;
	}
}
