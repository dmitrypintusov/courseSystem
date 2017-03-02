package by.pvt.pintusov.courses.services;

import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.AbstractEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Service interface
 * @author dpintusov
 * @version 1.2
 */

public interface IService <T extends AbstractEntity> {

	Serializable saveOrUpdate(T entity) throws ServiceException;
	T getById(Integer id) throws ServiceException;
	List<T> getAll () throws ServiceException;
	void delete(Integer id) throws ServiceException;
}

