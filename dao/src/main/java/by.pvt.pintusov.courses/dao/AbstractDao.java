package by.pvt.pintusov.courses.dao;

import by.pvt.pintusov.courses.constants.DaoConstants;
import by.pvt.pintusov.courses.exceptions.DaoException;
import by.pvt.pintusov.courses.pojos.AbstractEntity;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Describes the abstract class AbstractDao
 * @param <T> - entity of AbstractEntity
 * @author dpintusov
 * @version 1.2
 */

public abstract class AbstractDao <T extends AbstractEntity> implements IDao <T> {
	private static Logger logger = Logger.getLogger(AbstractDao.class);
	private Class persistentClass;

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Constructor for creating Hibernate connection
	 * for abstract class of Entity
	 * @param persistentClass - abstract class of Entity
	 * @param sessionFactory - Hibernate session factory
	 */
	protected AbstractDao(Class persistentClass, SessionFactory sessionFactory){
		this.persistentClass = persistentClass;
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Getting current session from session factory
	 * @return Session
	 */
	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Saving or updating entity to/in database
	 * @param entity - abstract Entity
	 * @return id - entity id in database
	 * @throws DaoException
	 */
	@Override
	public Serializable saveOrUpdate(T entity) throws DaoException {
		Serializable id;
		try {
			Session session = getCurrentSession();
			session.saveOrUpdate(entity);
			id = session.getIdentifier(entity);
		}
		catch (HibernateException e) {
			logger.error(DaoConstants.ERROR_DAO + e);
			throw new DaoException();
		}
		return id;
	}

	/**
	 * Getting entity by id
	 * @param id - abstract Entity id
	 * @return entity - persistent entity from database
	 * @throws DaoException
	 */
	@Override
	public T getById(Integer id) throws DaoException {
		T entity;
		try {
			Session session = getCurrentSession();
			entity = (T) session.get(persistentClass, id);
		} catch (HibernateException e) {
			logger.error(DaoConstants.ERROR_DAO + e);
			throw new DaoException();
		}
		return entity;
	}

	/**
	 * Loading entity from database
	 * @param id - abstract Entity id
	 * @return entity - persistent entity from database
	 * @throws DaoException
	 */
	@Override
	public T load(Integer id) throws DaoException {
		T entity;
		try {
			Session session = getCurrentSession();
			entity = (T) session.load(persistentClass, id, LockMode.READ);
		} catch (HibernateException e) {
			logger.error(DaoConstants.ERROR_DAO + e);
			throw new DaoException();
		}
		return entity;
	}

	/**
	 * Deleting entity from database
	 * @param id - abstract Entity id to delete
	 * @throws DaoException
	 */
	@Override
	public void delete(Integer id) throws DaoException {
		try {
			Session session = getCurrentSession();
			T entity = (T) session.get(persistentClass, id);
			session.delete(entity);
		}catch (HibernateException e) {
			logger.error(DaoConstants.ERROR_DAO + e);
			throw new DaoException();
		}
	}

	/**
	 * Getting List of abstract Entity
	 * @return list - list of entities
	 * @throws DaoException
	 */
	@Override
	public List<T> getAll() throws DaoException {
		List <T> list;
		try {
			Session session = getCurrentSession();
			Criteria criteria = session.createCriteria(persistentClass);
			list = criteria.list();
		} catch (HibernateException e) {
			logger.error(DaoConstants.ERROR_DAO + e);
			throw new DaoException();
		}
		return list;
	}

	/**
	 * Getting abstract Entities from database
	 * for creating pagination
	 * @param recordsPerPage - records of entities
	 * @param pageNumber - number of pages
	 * @return list - list of entities for pagination
	 * @throws DaoException
	 */
	@Override
	public List<T> getAllToPage(int recordsPerPage, int pageNumber) throws DaoException {
		List <T> list;
		try {
			Session session = getCurrentSession();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.setFirstResult((pageNumber-1) * recordsPerPage);
			criteria.setMaxResults(recordsPerPage);
			list = criteria.list();
		} catch (HibernateException e) {
			logger.error(DaoConstants.ERROR_DAO + e);
			throw new DaoException();
		}
		return list;
	}

	/**
	 * Getting number of records in database
	 * for abstract Entity
	 * @return records - number of records
	 * @throws DaoException
	 */
	@Override
	public Long getNumberRecords() throws DaoException {
		Long records;
		try {
			Session session = getCurrentSession();
			Criteria criteria = session.createCriteria(persistentClass);
			Projection projection = Projections.rowCount();
			criteria.setProjection(projection);
			records = (Long) criteria.uniqueResult();
		} catch (HibernateException e) {
			logger.error(DaoConstants.ERROR_DAO + e);
			throw new DaoException();
		}
		return records;
	}
}
