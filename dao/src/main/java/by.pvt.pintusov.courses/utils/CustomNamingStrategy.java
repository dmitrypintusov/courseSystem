package by.pvt.pintusov.courses.utils;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * Custom naming strategy
 * used for adding prefixes to tables and columns
 * Project: courses
 * Created by: dpintusov
 * Date: 21.01.17.
 */
public class CustomNamingStrategy extends DefaultNamingStrategy {

	@Override
	public String classToTableName(String className) {
		return "T_" + super.classToTableName(className).toUpperCase();
	}

	@Override
	public String propertyToColumnName(String propertyName) {
		return "F_" + super.propertyToColumnName(propertyName).toUpperCase();
	}
}
