package by.pvt.pintusov.courses.utils;

import by.pvt.pintusov.courses.constants.OrderingConstants;

/**
 * Ordering util
 * Project name: courses
 * Created by dpintusov
 * Date: 15.02.2017.
 */

public class OrderingUtil {

	private OrderingUtil () {}

	/**
	 * Define ordering parameter
	 * @param ordering - type of ordering
	 * @return orderBy parameter
	 */
	public static String defineOrderingType (String ordering) {
		String orderBy;
		switch (ordering) {
			case "courseName":
				orderBy = OrderingConstants.ORDER_BY_COURSE_NAME;
				break;
			case "courseStatus":
				orderBy = OrderingConstants.ORDER_BY_COURSE_STATUS;
				break;
			case "hours":
				orderBy = OrderingConstants.ORDER_BY_HOURS;
				break;
			default:
				orderBy = OrderingConstants.ORDER_BY_COURSE_NAME;
		}
		return orderBy;
	}

	/**
	 * Define ordering direction
	 * @param direction - direction parameter
	 * @return orderDirection parameter
	 */
	public static String defineOrderingDirection (String direction) {
		String orderDirection;
		switch (direction) {
			case "asc":
				orderDirection = OrderingConstants.ORDER_ASC;
				break;
			case "desc":
				orderDirection = OrderingConstants.ORDER_DESC;
				break;
			default:
				orderDirection = OrderingConstants.ORDER_ASC;
		}
		return orderDirection;
	}
}
