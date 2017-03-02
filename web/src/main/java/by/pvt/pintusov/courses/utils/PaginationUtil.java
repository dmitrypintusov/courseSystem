package by.pvt.pintusov.courses.utils;

import by.pvt.pintusov.courses.constants.OrderingConstants;
import by.pvt.pintusov.courses.filters.PaginationFilter;

/**
 * Pagination Util
 * Project: courses
 * Created by: dpintusov
 * Date: 16.02.17.
 */

public class PaginationUtil {

	/**
	 * Define ordering parameter
	 * @param ordering - ordering type
	 * @param direction - ordering direction
	 * @param currentPage - current page of user
	 * @param recordsPerPage - records per page
	 * @return
	 */
	public static PaginationFilter defineParameters (String ordering, String direction, Integer currentPage, Integer recordsPerPage) {
		PaginationFilter filter = new PaginationFilter();
		if (ordering != null) {
			filter.setOrdering(ordering);
		} else {
			filter.setOrdering(OrderingConstants.ORDER_BY_COURSE_NAME);
		}

		if (recordsPerPage == null) {
			filter.setRecordsPerPage(3);
		} else {
			filter.setRecordsPerPage(recordsPerPage);
		}

		if (currentPage == null) {
			filter.setCurrentPage(1);
		} else {
			filter.setCurrentPage(currentPage);
		}

		if (direction == null) {
			filter.setDirection(OrderingConstants.ORDER_ASC);
		} else {
			filter.setDirection(direction);
		}
		return filter;
	}
}
